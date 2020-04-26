# frozen_string_literal: true

require 'test_helper'

class Web::Admin::UsersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @admin = users(:admin)
    sign_in(@admin)
  end

  test '#index' do
    get admin_users_path
    assert_response :success
  end

  test '#ban' do
    user = users(:one)

    patch admin_user_ban_path(user)
    assert_response :redirect

    user.reload
    assert user.banned?
  end

  test '#unban' do
    user = users(:banned)

    patch admin_user_unban_path(user)
    assert_response :redirect

    user.reload
    assert user.permitted?
  end
end
