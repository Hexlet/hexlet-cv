# frozen_string_literal: true

require 'test_helper'

class Web::Admin::CareersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @admin = users(:admin)
    sign_in(@admin)
  end

  test '#index' do
    get admin_careers_path
    assert_response :success
  end

  test '#show' do
    career = career(:one)
    get admin_career_path(career)
    assert_response :success
  end

  test '#new' do
    get new_admin_career_path
    assert_response :success
  end

  test '#create' do
    attrs = FactoryBot.attributes_for :career
    post admin_careers_path, params: { career: attrs }
    assert_redirected_to admin_careers_path

    career = Career.find_by(name: attrs[:name])

    assert { career }
  end

  test '#edit' do
    career = career(:one)
    get edit_admin_career_path(career)
    assert_response :success
  end

  test '#update' do
    career = career(:two)
    attrs = FactoryBot.attributes_for :career
    attrs[:items_attributes] = [
      {
        order: 1,
        step_id: step(:one).id
      }
    ]
    patch admin_career_path(career), params: { career: attrs }
    assert { career.items.size == 1 }
  end
end
