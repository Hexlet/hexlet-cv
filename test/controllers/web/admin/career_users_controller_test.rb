# frozen_string_literal: true

require 'test_helper'

class Web::Admin::CareerUsersControllerTest < ActionDispatch::IntegrationTest
  setup do
    admin = users(:admin)
    sign_in(admin)
  end

  test '#index' do
    get admin_career_users_path

    assert_response :success
  end

  test '#show' do
    user = users(:full)

    get admin_career_user_path(user)

    assert_response :success
  end

  test '#archived' do
    get archived_admin_career_users_path

    assert_response :success
  end

  test '#finished' do
    get finished_admin_career_users_path

    assert_response :success
  end
end
