# frozen_string_literal: true

require 'test_helper'

class Web::UsersControllerTest < ActionDispatch::IntegrationTest
  test '#index' do
    get users_path
    assert_response :success
  end

  test '#show' do
    user = users(:one)
    get user_path(user)
    assert_response :success
  end

  test '#user anonimus' do
    user = users(:without_last_name_and_first_name)
    sign_in(user)

    get users_path
    assert_redirected_to edit_account_profile_path
  end
end
