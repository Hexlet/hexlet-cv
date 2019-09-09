# frozen_string_literal: true

require 'test_helper'

class Web::Account::ProfilesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#show' do
    get account_profile_path
    assert_response :success
  end

  test '#update' do
    profile_params = {
      first_name: Faker::Name.first_name,
      last_name: Faker::Name.last_name
    }
    patch account_profile_path(@user), params: { user: profile_params }
    assert_response :redirect

    @user.reload

    assert_equal @user.first_name, profile_params[:first_name]
    assert_equal @user.last_name, profile_params[:last_name]
  end
end
