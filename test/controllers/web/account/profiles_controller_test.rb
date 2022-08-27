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
    patch account_profile_path(@user), params: { user: attrs }
    assert_response :redirect

    @user.reload

    assert { @user.first_name == attrs[:first_name] }
    assert { @user.last_name == attrs[:last_name] }
    assert { @user.about == attrs[:about] }
  end

  test '#check_box' do
    attrs = %w[work_course resume hexlet]
    patch check_box_account_profile_path, params: { user: { check_boxes: attrs } }
    assert { @user.check_boxes.include? 'resume' }
    assert { @user.check_boxes.include? 'work_course' }
    assert { @user.check_boxes.include? 'hexlet' }
    assert_not { @user.check_boxes.include? 'default' }
  end
end
