# frozen_string_literal: true

require 'test_helper'

class CareerMemberMailerTest < ActionMailer::TestCase
  setup do
    @career_member = career_members(:member_full)
    @user = @career_member.user
  end

  test 'new career member' do
    mail = CareerMemberMailer.with(career_member: @career_member, user: @user).new_career_member_email
    assert { @user.email == mail.to.first }
  end

  test 'career member finish' do
    career_member = career_members(:member_with_github)
    user = career_member.user
    mail = CareerMemberMailer.with(career_member:, user:).career_member_finish
    assert { user.email == mail.to.first }
  end

  test '#next_career_step_open_source' do
    mail = CareerMemberMailer.with(career_member: @career_member, user: @user).next_step_open_source
    assert { @user.email == mail.to.first }
  end
end
