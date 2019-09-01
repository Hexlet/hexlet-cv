# frozen_string_literal: true

require 'test_helper'

class Web::Account::ResumesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    @session = login(@user)
  end

  test '#index' do
    @session.get account_resumes_path
    @session.assert_response :success
  end

  test '#new' do
    @session.get new_account_resume_path
    @session.assert_response :success
  end

  test '#create' do
    attrs = {
      name: 'some name',
      summary: Faker::Lorem.paragraph_by_chars(number: 300),
      skills_description: 'skills',
      github_url: 'github',
      awards_description: 'awards'
    }
    @session.post account_resumes_path, params: { resume: attrs }
    @session.assert_response :redirect

    resume = Resume.find_by! name: attrs[:name]
    assert resume
  end

  test '#update' do
    resume = resumes(:one)
    attrs = {
      name: 'another name'
    }
    @session.patch account_resume_path(resume), params: { resume: attrs }
    @session.assert_response :redirect

    resume.reload
    # TODO: switch to power-assert
    assert resume.name == attrs[:name]
  end
end
