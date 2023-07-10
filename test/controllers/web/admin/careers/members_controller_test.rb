# frozen_string_literal: true

require 'test_helper'

class Web::Admin::Careers::MembersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @admin = users(:admin)
    @user = users(:two)
    @career = careers(:devops)
    sign_in(@admin)
  end

  test '#create' do
    attrs = {
      user_id: @user.id
    }

    post admin_career_members_path(@career), params: { career_member: attrs }
    assert_redirected_to admin_career_path(@career)

    member = Career::Member.find_by(user_id: attrs[:user_id])
    notification = Notification.find_by(user: @user, resource: member, kind: :new_career_member)

    assert { member }
    assert { notification }
  end

  test '#archive' do
    member = career_members(:member_one)

    patch archive_admin_career_member_path(@career, member)

    assert_redirected_to admin_career_path(@career)
    member.reload
    assert { member.archived? }
  end

  test '#activate' do
    member = career_members(:archived_member)

    patch activate_admin_career_member_path(@career, member)

    assert_redirected_to admin_career_member_users_path
    member.reload
    assert { member.active? }
  end

  test 'new career member email' do
    attrs = {
      user_id: @user.id
    }

    assert_emails 1 do
      post admin_career_members_path(@career), params: { career_member: attrs }
    end
  end

  test 'with user at disabled email delivery' do
    user = users(:mail_delivery_disabled)
    attrs = {
      user_id: user.id
    }

    assert_emails 0 do
      post admin_career_members_path(@career), params: { career_member: attrs }
    end
  end
end
