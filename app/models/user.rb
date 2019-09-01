# frozen_string_literal: true

class User < ApplicationRecord
  has_secure_password

  has_many :resumes, dependent: :destroy

  def guest?
    false
  end

  def to_s
    "#{first_name} #{last_name}"
  end
end
