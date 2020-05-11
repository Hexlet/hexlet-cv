# frozen_string_literal: true

require 'test_helper'

class Web::Admin::HomeControllerTest < ActionDispatch::IntegrationTest
  test '#index' do
    admin = users(:admin)
    sign_in(admin)

    get admin_root_path
    assert_response :success
  end

  test '#index without sign_in' do
    get admin_root_path
    assert_response :redirect
  end

  test '#index sign_in as user' do
    user = users(:one)
    sign_in(user)

    get admin_root_path
    assert_response :redirect
  end
end
