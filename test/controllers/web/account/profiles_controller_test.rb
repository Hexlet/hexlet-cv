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

  test '#edit user anonimus' do
    user = users(:without_last_name_and_first_name)
    sign_in(user)

    get edit_account_profile_path
    assert_response :success
  end

  test '#update' do
    attrs = FactoryBot.attributes_for :user
    patch account_profile_path(@user, locale: I18n.locale), params: { web_account_profile_form: attrs }
    assert_response :redirect

    @user.reload

    assert { @user.first_name == attrs[:first_name] }
    assert { @user.last_name == attrs[:last_name] }
    assert { @user.about == attrs[:about] }
  end

  test '#update without required attributes' do
    user = users(:without_last_name_and_first_name)
    sign_in(user)
    attrs = FactoryBot.attributes_for :user
    patch account_profile_path(user, locale: I18n.locale), params: { web_account_profile_form: attrs.except(:last_name, :first_name) }
    assert_response :unprocessable_entity
  end

  test '#update user anonimus' do
    user = users(:without_last_name_and_first_name)
    sign_in(user)
    attrs = FactoryBot.attributes_for :user
    patch account_profile_path(user, locale: I18n.locale), params: { web_account_profile_form: attrs }
    assert_response :redirect
  end

  test 'destroy' do
    delete account_profile_path
    assert_response :redirect

    @user.reload

    assert { @user.removed? }
    # TODO: add signed out checking
  end
end
