# frozen_string_literal: true

require 'test_helper'

class Web::OmniauthControllerTest < ActionDispatch::IntegrationTest
  test '#github (new user)' do
    sign_with_github
    assert_response :redirect
    assert { User.exists?(email: 'github@github.com') }
  end

  test '#github (exist user)' do
    user = users(:with_github)
    sign_with_github
    assert_response :redirect
  end
end
