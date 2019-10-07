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
end
