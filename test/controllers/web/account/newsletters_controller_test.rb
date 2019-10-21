# frozen_string_literal: true

require 'test_helper'

class Web::Account::NewslettersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#edit' do
    get edit_account_newsletters_path
    assert_response :success
  end

  test '#update' do
    attrs = { resume_mailer: false }
    patch account_newsletters_path(@user), params: { user: attrs }
    assert_response :redirect

    @user.reload

    assert { @user.resume_mailer == attrs[:resume_mailer] }
  end
end
