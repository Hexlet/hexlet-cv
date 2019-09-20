# frozen_string_literal: true

class User < ApplicationRecord
  include UserRepository
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable, :trackable and :omniauthable
  devise :database_authenticatable, :registerable, :confirmable, :lockable,
         :recoverable, :rememberable, :validatable, :trackable,
         :omniauthable, omniauth_providers: %i[ github ]
  # has_secure_password

  has_many :resumes, dependent: :destroy
  has_many :resume_answers, class_name: 'Resume::Answer', dependent: :destroy
  has_many :resume_comments, class_name: 'Resume::Comment', dependent: :destroy

  def from_omniauth(auth)
    users = where('email = ? OR (provider = ? AND uid = ?)',
                  auth.info.email,
                  auth.provider,
                  auth.uid)

    users.first_or_initialize.tap { |user| user.from_provider(auth) }
  end

  # def guest?
  #   false
  # end

  def to_s
    "#{first_name} #{last_name}"
  end

  # NOTE: https://github.com/plataformatec/devise#activejob-integration
  # def send_devise_notification(notification, *args)
  #   devise_mailer.send(notification, self, *args).deliver_later
  # end
end
