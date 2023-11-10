# frozen_string_literal: true

require 'test_helper'

class Web::OmniauthCallbacksControllerTest < ActionDispatch::IntegrationTest
  setup do
    OmniAuth.config.add_mock(
      :github,
      provider: 'github',
      uid: '12345',
      info: { name: 'Github User', email: 'github@github.com' }
    )
  end

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

  test '#github user without name' do
    OmniAuth.config.add_mock(
      :github,
      provider: 'github',
      uid: '54321',
      info: { email: 'without_last_name_and_first_name@email.com' }
    )
    post user_github_omniauth_callback_path

    assert_redirected_to edit_account_profile_path
  end
end
