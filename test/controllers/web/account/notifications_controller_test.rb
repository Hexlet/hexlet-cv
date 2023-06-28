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
    notification = notifications(:resume_comment_one_by_one)
    patch account_notification_path(notification)

    assert_redirected_to account_notifications_path

    notification.reload

    assert { notification.read? }
  end
end
