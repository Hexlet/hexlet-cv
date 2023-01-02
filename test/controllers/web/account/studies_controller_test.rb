# frozen_string_literal: true

require 'test_helper'

class Web::Account::StudiesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#edit' do
    get edit_account_study_path
    assert_response :success
  end

  test '#update' do
    attrs = FactoryBot.attributes_for :user
    patch account_study_path params: { profile: attrs }
    assert_response :redirect

    @user.reload

    assert { @user.strides == attrs[:strides] }
  end
end
