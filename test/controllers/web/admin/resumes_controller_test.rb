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

  test '#archive' do
    resume = resumes(:one)

    patch archive_admin_resume_path(resume)

    assert_redirected_to admin_resumes_path(q: { id_eq: resume.id })
    resume.reload

    assert { resume.archived? }
  end

  test '#restore' do
    resume = resumes(:one_archived)

    patch restore_admin_resume_path(resume)

    assert_redirected_to admin_resumes_path(q: { id_eq: resume.id })
    resume.reload

    assert { resume.published? }
  end

  test '#export vacancies to csv' do
    get admin_resumes_path(format: :csv)
    assert_response :success
  end
end
