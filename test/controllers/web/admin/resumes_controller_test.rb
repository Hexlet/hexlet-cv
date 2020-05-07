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

  test '#edit' do
    resume = resumes(:one)
    get edit_admin_resume_path(resume)
    assert_response :success
  end

  test '#update' do
    resume = resumes(:one)
    work = resume_works(:one)

    new_work_company = 'another name'
    new_resume_name = 'another name'

    attrs = {
      name: new_resume_name,
      works_attributes: {
        work.id => work.attributes.merge(company: new_work_company)
      }
    }
    patch admin_resume_path(resume), params: { resume: attrs }
    assert_response :redirect

    resume.reload
    work.reload
    assert { resume.name == new_resume_name }
    assert { work.company == new_work_company }
  end

  test 'should archive' do
    resume = resumes(:one)

    params = {
      archive: true,
      resume: { name: resume.name }
    }
    patch admin_resume_path(resume), params: params
    assert_response :redirect

    resume.reload
    assert { resume.archived? }
  end

  test 'should restore' do
    resume = resumes(:archived)

    params = {
      restore: true,
      resume: { name: resume.name }
    }
    patch admin_resume_path(resume), params: params
    assert_response :redirect

    resume.reload
    assert { resume.available? }
  end
end
