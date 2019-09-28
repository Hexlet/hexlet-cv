# frozen_string_literal: true

class User < ApplicationRecord
  include UserRepository
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable, :trackable and :omniauthable
  devise :database_authenticatable, :registerable, :confirmable, :lockable,
         :recoverable, :rememberable, :validatable, :trackable,
         :omniauthable, omniauth_providers: %i[github]
  # has_secure_password

  has_many :resumes, dependent: :destroy
  has_many :resume_answers, class_name: 'Resume::Answer', dependent: :destroy
  has_many :resume_comments, class_name: 'Resume::Comment', dependent: :destroy
  has_many :notifications, dependent: :destroy

  def self.from_omniauth(auth)
    if (exist_user = User.find_by(email: auth.info.email))
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
        user.first_name, user.last_name = auth.info.name.split(' ')
        user.skip_confirmation!
      end
    end
  end

  # def guest?
  #   false
  # end

  def to_s
    name = "#{first_name} #{last_name}"
    name.strip.empty? ? 'Anonymous' : name
  end

  # NOTE: https://github.com/plataformatec/devise#activejob-integration
  # def send_devise_notification(notification, *args)
  #   devise_mailer.send(notification, self, *args).deliver_later
  # end
end
