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

    work_attrs = FactoryBot.attributes_for 'resume/work'
    attrs = FactoryBot.attributes_for(:resume,
                                      works_attributes: { work.id => work.attributes.merge(company: work_attrs[:company]) })
    patch admin_resume_path(resume), params: { resume: attrs }
    assert_response :redirect

    resume.reload
    work.reload
    assert { resume.name == attrs[:name] }
    assert { work.company == work_attrs[:company] }
  end

  test 'should archive' do
    resume = resumes(:one)

    params = {
      resume: { state_event: :archive }
    }
    patch admin_resume_path(resume), params: params
    assert_response :redirect

    resume.reload
    assert { resume.archived? }
  end

  test 'should restore' do
    resume = resumes(:one_archived)

    params = {
      resume: { state_event: :restore }
    }
    patch admin_resume_path(resume), params: params
    assert_response :redirect

    resume.reload
    assert { resume.published? }
  end
end
