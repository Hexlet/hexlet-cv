# frozen_string_literal: true

require 'test_helper'

class UserTest < ActiveSupport::TestCase
  test 'new user with default attribute resume_mail_enabled' do
    attrs = {
      first_name: 'Vasy',
      last_name: 'Vetrov',
      email: 'vasy@mail.ru',
      password: '123456',
      confirmed_at: Time.current,
      role: :user,
      state: 'permitted'
    }

    user = User.new(attrs)
    user.save!

    assert { user.valid? }
    assert { user.resume_mail_enabled }
  end
end
