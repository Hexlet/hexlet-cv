class User < ApplicationRecord
  has_secure_password

  has_many :resumes

  def guest?
    false
  end

  def to_s
    "#{first_name} #{last_name}"
  end
end
