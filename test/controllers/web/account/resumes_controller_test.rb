# frozen_string_literal: true

require 'test_helper'

class Web::Account::ResumesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#index' do
    get account_resumes_path
    assert_response :success
  end

  test '#new' do
    get new_account_resume_path
    assert_response :success
  end

  test '#create' do
    attrs = {
      name: 'some name',
      summary: Faker::Lorem.paragraph_by_chars(number: 300),
      skills_description: 'skills',
      github_url: 'github',
      awards_description: 'awards',
      english_fluency: 'dont_know'
    }
    post account_resumes_path, params: { resume: attrs }
    assert_response :redirect

    resume = Resume.find_by! name: attrs[:name]
    assert resume
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
    assert resume.name == new_resume_name
    assert work.company == new_work_company
  end
end
