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

  test '#ban' do
    resume = resumes(:one)

    patch admin_resume_ban_path(resume)
    assert_response :redirect

    resume.reload
    assert resume.banned?
  end

  test '#unban' do
    resume = resumes(:banned)

    patch admin_resume_unban_path(resume)
    assert_response :redirect

    resume.reload
    assert resume.permitted?
  end
end
