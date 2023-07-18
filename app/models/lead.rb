# frozen_string_literal: true

class Lead < ApplicationRecord
  validates :user_name, :phone_number, presence: true
  validates :email, 'valid_email_2/email': true, presence: true, uniqueness: true
end
