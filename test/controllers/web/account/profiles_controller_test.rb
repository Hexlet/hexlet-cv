# frozen_string_literal: true

require 'test_helper'

class Web::Account::ProfilesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#edit' do
    get edit_account_profile_path
    assert_response :success
  end

  test '#update' do
    attrs = FactoryBot.attributes_for :user
    patch account_profile_path(@user, locale: I18n.locale), params: { user: attrs }
    assert_response :redirect

    @user.reload

    assert { @user.first_name == attrs[:first_name] }
    assert { @user.last_name == attrs[:last_name] }
    assert { @user.about == attrs[:about] }
  end
end
