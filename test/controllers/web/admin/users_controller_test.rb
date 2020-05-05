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

  test '#change_admin_state ban' do
    user = users(:one)

    patch change_admin_state_admin_user_path(user), params: { event: :ban }
    assert_response :redirect

    user.reload
    assert { user.banned? }
  end

  test '#change_admin_state unban' do
    user = users(:banned)

    patch change_admin_state_admin_user_path(user), params: { event: :unban }
    assert_response :redirect

    user.reload
    assert { user.permitted? }
  end
end
