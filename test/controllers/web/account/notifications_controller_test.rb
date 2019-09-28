# frozen_string_literal: true

require 'test_helper'

class Web::Account::NotificationsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#index' do
    get account_notifications_url
    assert_response :success
  end
end
