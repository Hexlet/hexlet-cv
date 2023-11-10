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

  test '#edit user anonimus' do
    user = users(:without_last_name_and_first_name)
    sign_in(user)

    get edit_account_newsletters_path
    assert_redirected_to edit_account_profile_path
  end

  test '#update' do
    attrs = { resume_mail_enabled: false }
    patch account_newsletters_path(@user, locale: I18n.locale), params: { user: attrs }
    assert_response :redirect

    @user.reload

    assert { @user.resume_mail_enabled == attrs[:resume_mail_enabled] }
  end
end
