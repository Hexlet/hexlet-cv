# frozen_string_literal: true

class Web::UserRegistrationForm < User
  include ActiveFormModel

  validates :first_name, :last_name, :email, :password, :password_confirmation, presence: true
  validates :first_name, :last_name, length: { maximum: 40 }
  validates :email, 'valid_email_2/email': true

  fields :first_name,
         :last_name,
         :email,
         :password,
         :password_confirmation
end
