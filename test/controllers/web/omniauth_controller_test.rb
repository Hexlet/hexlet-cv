# frozen_string_literal: true

require 'test_helper'

class Web::OmniauthControllerTest < ActionDispatch::IntegrationTest
  test '#github' do
    sign_with_github
    assert_response :redirect
    assert { User.exists?(email: 'github@github.com') }
  end
end
