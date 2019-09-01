# frozen_string_literal: true

require 'test_helper'

class Web::SessionsControllerTest < ActionDispatch::IntegrationTest
  test '#new' do
    get new_session_path
    assert_response :success
  end

  test '#create' do
    user = users(:one)
    attrs = { email: user.email }
    post session_path, params: { user: attrs }
    assert_response :redirect

    assert signed_in?
  end

  test '#destroy' do
    user = users(:one)
    user_session = login(user)
    user_session.delete session_path
    user_session.assert_response :redirect

    assert_not user_session.signed_in?
  end
end
