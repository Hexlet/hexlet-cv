# frozen_string_literal: true

require 'test_helper'

class CareerMemberMailerTest < ActionMailer::TestCase
  test 'new career member' do
    career_member = career_members(:member_full)
    user = career_member.user
    mail = CareerMemberMailer.with(career_member:, user:).new_career_member_email
    assert { user.email == mail.to.first }
  end
end
