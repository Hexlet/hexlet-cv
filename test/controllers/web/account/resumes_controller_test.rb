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
      link: 'some link'
    }
    @session.post account_resumes_path, params: { resume: attrs }
    @session.assert_response :redirect

    resume = Resume.find_by! link: attrs[:link]
    assert resume
  end
end
