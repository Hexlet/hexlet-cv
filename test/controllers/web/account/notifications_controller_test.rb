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

  test '#update' do
    post read_all_account_notifications_url

    assert_response :redirect
    assert { @user.notifications.unread.empty? }
  end
end
