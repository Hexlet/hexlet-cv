# frozen_string_literal: true

# == Schema Information
#
# Table name: users
#
#  id                        :integer          not null, primary key
#  about                     :string
#  bounced_email             :boolean
#  confirmation_sent_at      :datetime
#  confirmation_token        :string
#  confirmed_at              :datetime
#  current_sign_in_at        :datetime
#  current_sign_in_ip        :string
#  email                     :string
#  email_disabled_delivery   :boolean
#  encrypted_password        :string           default(""), not null
#  failed_attempts           :integer          default(0), not null
#  first_name                :string
#  last_name                 :string
#  last_sign_in_at           :datetime
#  last_sign_in_ip           :string
#  locale                    :string
#  locked_at                 :datetime
#  marked_as_spam            :boolean
#  provider                  :string
#  remember_created_at       :datetime
#  reset_password_sent_at    :datetime
#  reset_password_token      :string
#  resume_answer_likes_count :integer          default(0), not null
#  resume_mail_enabled       :boolean
#  role                      :string
#  sign_in_count             :integer          default(0), not null
#  state                     :string
#  uid                       :string
#  unconfirmed_email         :string
#  unlock_token              :string
#  created_at                :datetime         not null
#  updated_at                :datetime         not null
#
# Indexes
#
#  index_users_on_confirmation_token    (confirmation_token) UNIQUE
#  index_users_on_email                 (email) UNIQUE
#  index_users_on_reset_password_token  (reset_password_token) UNIQUE
#  index_users_on_unlock_token          (unlock_token) UNIQUE
#
class User < ApplicationRecord
  include StateConcern
  extend Enumerize
  include UserRepository
  include UserPresenter

  validates :email, 'valid_email_2/email': true

  # https://github.com/heartcombo/devise/wiki/How-To:-Add-an-Admin-Role
  enumerize :role, in: %i[user admin], default: :user, predicates: true, scope: true

  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable, :trackable and :omniauthable
  devise :database_authenticatable, :registerable, :confirmable, :lockable,
         :recoverable, :rememberable, :validatable, :trackable,
         :omniauthable, omniauth_providers: %i[github]
  # has_secure_password

  has_many :resumes, dependent: :destroy
  has_many :vacancies, dependent: :restrict_with_exception, foreign_key: 'creator_id', inverse_of: :creator
  has_many :resume_answers, class_name: 'Resume::Answer', dependent: :destroy
  has_many :resume_answer_likes, through: :resume_answers, source: :likes
  has_many :resume_comments, class_name: 'Resume::Comment', dependent: :destroy
  has_many :notifications, dependent: :destroy
  has_many :career_members, class_name: 'Career::Member', dependent: :destroy
  has_many :careers, through: :career_members
  has_many :unread_notifications, -> { unread }, class_name: 'Notification', inverse_of: :user, dependent: :nullify
  has_one :active_career_member, -> { active }, class_name: 'Career::Member', inverse_of: :user, dependent: :nullify

  aasm :state, column: :state do
    state :permitted, initial: true
    state :banned

    event :ban do
      transitions from: %i[permitted], to: :banned
    end

    event :unban do
      transitions from: %i[banned], to: :permitted
    end
  end

  # https://github.com/heartcombo/devise/wiki/How-to:-Soft-delete-a-user-when-user-deletes-account
  def active_for_authentication?
    super && permitted?
  end

  def initialize(attribute = nil)
    defaults = {
      resume_mail_enabled: true,
      locale: I18n.locale
    }

    attrs_with_defaults = attribute ? defaults.merge(attribute) : defaults
    super(attrs_with_defaults)
  end

  # provide a custom message for a banned user
  def inactive_message
    banned? ? :banned : super
  end

  def guest?; end

  def self.from_omniauth(auth)
    exist_user = User.find_by(email: auth.info.email)
    if exist_user
      exist_user.provider = auth.provider
      exist_user.uid = auth.uid
      exist_user.save
      exist_user
    else
      where(provider: auth.provider, uid: auth.uid).first_or_create do |user|
        user.email = auth.info.email
        user.provider = auth.provider
        user.uid = auth.uid
        user.password = Devise.friendly_token[0, 20]
        user.first_name, user.last_name = auth.info.name.split
        user.skip_confirmation!
      end
    end
  end

  def can_send_email?
    !email_disabled_delivery && !unconfirmed_email && resume_mail_enabled
  end

  def anonimus?
    last_name.nil? || first_name.nil?
  end

  def self.ransackable_attributes(_auth_object = nil)
    %w[first_name last_name email]
  end

  def self.ransackable_associations(_auth_object = nil)
    %w[resumes careers career_members]
  end

  # NOTE: https://github.com/plataformatec/devise#activejob-integration
  # def send_devise_notification(notification, *args)
  #   devise_mailer.send(notification, self, *args).deliver_later
  # end
end
