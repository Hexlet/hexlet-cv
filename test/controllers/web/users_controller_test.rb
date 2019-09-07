require 'test_helper'

class Web::UsersControllerTest < ActionDispatch::IntegrationTest
  test '#show' do
    user = users(:one)
    get user_path(user)
    assert_response :success
  end
end
