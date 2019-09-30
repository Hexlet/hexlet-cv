# frozen_string_literal: true

require 'test_helper'

class Web::Account::ResumesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#new' do
    get new_account_resume_path
    assert_response :success
  end

  test '#create' do
    attrs = FactoryBot.attributes_for :resume
    post account_resumes_path, params: { resume: attrs }
    assert_response :redirect

    assert { Resume.exists?(attrs.slice(:name)) }
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
    patch account_resume_path(resume), params: { resume: attrs }
    assert_response :redirect

    resume.reload
    work.reload
    # TODO: switch to power-assert
    assert { resume.name == new_resume_name }
    assert { work.company == new_work_company }
  end

  test 'should publish published resume' do
    resume = resumes(:one)
    attrs = FactoryBot.attributes_for :resume

    params = {
      publish: true,
      resume: { name: attrs[:name] }
    }

    patch account_resume_path(resume), params: params
    assert_response :redirect
  end
end
