# frozen_string_literal: true

require 'test_helper'

class Web::Admin::ResumesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @admin = users(:admin)
    sign_in(@admin)
  end

  test '#index' do
    get admin_resumes_path
    assert_response :success
  end

  test '#change_admin_state archive' do
    resume = resumes(:one)

    patch change_admin_state_admin_resume_path(resume), params: { event: :archive }
    assert_response :redirect

    resume.reload
    assert { resume.archived? }
  end

  test '#change_admin_state restore' do
    resume = resumes(:archived)

    patch change_admin_state_admin_resume_path(resume), params: { event: :restore }
    assert_response :redirect

    resume.reload
    assert { resume.available? }
  end
end
