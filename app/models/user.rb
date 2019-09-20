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

  def self.from_omniauth(auth)
    where(email: auth.info.email).or(where(provider: auth.provider, uid: auth.uid)).first_or_create do |user|
      user.email = auth.info.email
      user.provider = auth.provider
      user.uid = auth.uid
      user.password = Devise.friendly_token[0, 20]
      user.first_name, user.last_name = auth.info.name.split(' ')
      user.skip_confirmation!
    end
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
