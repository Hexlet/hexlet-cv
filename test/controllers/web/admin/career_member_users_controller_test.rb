# frozen_string_literal: true

require 'test_helper'

class Web::Admin::CareerMemberUsersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:two)
    @career = careers(:analytics)
    @admin = users(:admin)
    sign_in(@admin)
  end

  test '#index' do
    get admin_career_member_users_path

    assert_response :success
  end

  test '#index format csv' do
    get admin_career_member_users_path(format: :csv)

    assert_response :success
  end

  test '#show' do
    user = users(:full)

    get admin_career_member_user_path(user)

    assert_response :success
  end

  test '#archived' do
    get archived_admin_career_member_users_path

    assert_response :success
  end

  test '#archived fomat csv' do
    get archived_admin_career_member_users_path(format: :csv)

    assert_response :success
  end

  test '#finished' do
    get finished_admin_career_member_users_path

    assert_response :success
  end

  test '#finished format csv' do
    get finished_admin_career_member_users_path(format: :csv)

    assert_response :success
  end

  test '#lost' do
    get lost_admin_career_member_users_path

    assert_response :success
  end

  test '#lost format csv' do
    get lost_admin_career_member_users_path(format: :csv)

    assert_response :success
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

    assert_redirected_to admin_career_member_users_path

    career_member = Career::Member.find_by(attrs)

    notification = Notification.find_by(user: @user, resource: career_member, kind: :new_career_member)
    event = Event.find_by(user: @user, resource: career_member, kind: :new_career_member)

    assert { career_member }
    assert { notification }
    assert { event }
  end

  test 'new member email' do
    attrs = {
      career_id: @career.id,
      user_id: @user.id
    }

    post admin_career_member_users_path, params: { career_member: attrs }

    career_member = Career::Member.find_by(attrs)
    event = Event.find_by(user: @user, resource: career_member, kind: :new_career_member)

    assert { event.sended? }
  end

  test 'with user at disabled email delivery' do
    user = users(:mail_delivery_disabled)
    attrs = {
      career_id: @career.id,
      user_id: user.id
    }

    post admin_career_member_users_path, params: { career_member: attrs }

    career_member = Career::Member.find_by(user_id: attrs[:user_id])
    event = Event.find_by(user:, resource: career_member, kind: :new_career_member)

    assert { event.unsended? }
  end
end
