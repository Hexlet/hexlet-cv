# frozen_string_literal: true

require 'test_helper'

class Web::OmniauthCallbacksControllerTest < ActionDispatch::IntegrationTest
  test '#github (new user)' do
    post user_github_omniauth_callback_path
    assert_response :redirect
    assert { User.exists?(email: 'github@github.com') }
  end

  test '#github (exist user)' do
    # users(:with_github)
    post user_github_omniauth_callback_path
    assert_response :redirect
    assert { User.exists?(email: 'github@github.com', uid: '12345', provider: 'github') }
  end
end
