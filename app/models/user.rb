# frozen_string_literal: true

class User < ApplicationRecord
  include StateConcern
  extend Enumerize
  include UserRepository
  include UserPresenter

  validates :email, 'valid_email_2/email': true

  # https://github.com/heartcombo/devise/wiki/How-To:-Add-an-Admin-Role
  enumerize :role, in: %i[user admin], default: :user, predicates: true, scope: true
  enumerize :strides, in: Career::GOALS, multiple: true
  serialize :strides, Array

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
      resume_mail_enabled: true
    }

    attrs_with_defaults = attribute ? defaults.merge(attribute) : defaults
    super(attrs_with_defaults)
  end

  # provide a custom message for a banned user
  def inactive_message
    banned? ? :banned : super
  end

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
    !email_disabled_delivery && !unconfirmed_email
  end

  # NOTE: https://github.com/plataformatec/devise#activejob-integration
  # def send_devise_notification(notification, *args)
  #   devise_mailer.send(notification, self, *args).deliver_later
  # end
end
