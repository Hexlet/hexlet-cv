# frozen_string_literal: true

class UserService
  class << self
    def remove!(user)
      ActiveRecord::Base.transaction do
        user.remove!

        user.email = nil
        user.first_name = nil
        user.last_name = nil
        user.reset_password_token = nil
        user.confirmation_token = nil

        user.save!
      end
    end
  end
end
