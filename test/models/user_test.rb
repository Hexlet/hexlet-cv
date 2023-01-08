# frozen_string_literal: true

require 'test_helper'

class UserTest < ActiveSupport::TestCase
  test 'new user with default attribute resume_mail_enabled' do
    attrs = FactoryBot.attributes_for :user

    user = User.new(attrs)
    user.save!

    assert { user.valid? }
    assert { user.resume_mail_enabled }
  end
end
