# frozen_string_literal: true

require 'test_helper'

class Web::Admin::CareerMemberUsersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:two)
    @career = careers(:analytics)
    @admin = users(:admin)
    sign_in(@admin)
  end

  test '#new' do
    get new_admin_career_member_user_path

    assert_response :success
  end

  test '#create' do
    attrs = {
      career_id: @career.id,
      user_id: @user.id
    }

    post admin_career_member_users_path, params: { career_member: attrs }

    assert_redirected_to admin_career_users_path

    career_member = Career::Member.find_by(attrs)

    notification = Notification.find_by(user: @user, resource: career_member, kind: :new_career_member)

    assert { career_member }
    assert { notification }
  end

  test 'new member email' do
    attrs = {
      career_id: @career.id,
      user_id: @user.id
    }

    assert_emails 1 do
      post admin_career_member_users_path, params: { career_member: attrs }
    end
  end
end
