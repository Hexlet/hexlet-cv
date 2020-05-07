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
    patch admin_user_path(user), params: { user: attrs }
    assert_response :redirect

    user.reload

    assert { user.first_name == attrs[:first_name] }
    assert { user.last_name == attrs[:last_name] }
    assert { user.about == attrs[:about] }
  end

  test 'should ban' do
    user = users(:one)

    params = {
      ban: true,
      user: { first_name: user.first_name }
    }
    patch admin_user_path(user), params: params
    assert_response :redirect

    user.reload
    assert { user.banned? }
  end

  test 'should unban' do
    user = users(:banned)

    params = {
      unban: true,
      user: { first_name: user.first_name }
    }
    patch admin_user_path(user), params: params
    assert_response :redirect

    user.reload
    assert { user.permitted? }
  end
end
