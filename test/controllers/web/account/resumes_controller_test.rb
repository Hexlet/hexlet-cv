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
      versions_attributes: [{
        content: '{}'
      }]
    }
    @session.post account_resumes_path, params: { resume: attrs }
    @session.assert_response :redirect

    resume = Resume.find_by! name: attrs[:name]
    assert resume
  end
end
