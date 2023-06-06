# frozen_string_literal: true

require 'test_helper'

class Web::Admin::Careers::StepsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @admin = users(:admin)
    @career = careers(:devops)
    @career_step = career_steps(:step_one)
    sign_in(@admin)
  end

  test '#new' do
    get new_admin_career_step_path(@career)
    assert_response :success
  end

  test '#show' do
    get admin_career_step_path(@career, @career_step)
    assert_response :success
  end

  test '#create' do
    attrs = FactoryBot.attributes_for :career_step
    post admin_career_steps_path(@career), params: { career_step: attrs }
    assert_redirected_to admin_career_path(@career)

    step = Career::Step.find_by(attrs)

    assert { step }
  end

  test '#edit' do
    get edit_admin_career_step_path(@career, @career_step)
    assert_response :success
  end

  test '#update' do
    attrs = FactoryBot.attributes_for :career_step

    patch admin_career_step_path(@career, @career_step), params: { career_step: { name: attrs[:name] } }

    assert_redirected_to admin_career_path(@career)
    @career_step.reload

    assert { @career_step.name == attrs[:name] }
  end
end
