# frozen_string_literal: true

require 'test_helper'

class Web::Admin::UsersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @admin = users(:admin)
    sign_in(@admin)
  end

  test '#index' do
    get admin_users_path
    assert_response :success
  end

  test '#update' do
    user = users(:one)
    attrs = FactoryBot.attributes_for :user
    patch admin_user_path(user), params: { web_admin_user_form: attrs }
    assert_response :redirect

    user.reload

    assert { user.first_name == attrs[:first_name] }
    assert { user.last_name == attrs[:last_name] }
    assert { user.about == attrs[:about] }
  end

  test '#update without required attributes' do
    user = users(:without_last_name_and_first_name)
    attrs = FactoryBot.attributes_for :user
    patch admin_user_path(user), params: { web_admin_user_form: attrs.except(:last_name, :first_name) }
    assert_response :unprocessable_entity
  end
end
