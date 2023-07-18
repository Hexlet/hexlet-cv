# frozen_string_literal: true

class Lead < ApplicationRecord
  validates :email, :phone_number, :user_name, presence: true
end
