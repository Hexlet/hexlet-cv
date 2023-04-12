# frozen_string_literal: true

require 'test_helper'

class Web::Admin::StepsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @admin = users(:admin)
    sign_in(@admin)
  end

  test '#index' do
    get admin_steps_path
    assert_response :success
  end

  test '#new' do
    get new_admin_step_path
    assert_response :success
  end

  test '#show' do
    step = step(:one)
    get admin_step_path(step)
    assert_response :success
  end

  test '#create' do
    attrs = FactoryBot.attributes_for :step
    post admin_steps_path, params: { step: attrs }
    assert_redirected_to admin_steps_path

    step = Step.find_by(attrs)

    assert { step }
  end

  test '#edit' do
    step = step(:one)
    get edit_admin_step_path(step)
    assert_response :success
  end

  test '#update' do
    attrs = FactoryBot.attributes_for :step
    step = step(:one)

    patch admin_step_path(step), params: { step: { name: attrs[:name] } }
    step.reload

    assert { step.name == attrs[:name] }
  end
end
