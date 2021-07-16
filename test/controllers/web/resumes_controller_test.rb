# frozen_string_literal: true

require 'test_helper'

class Web::ResumesControllerTest < ActionDispatch::IntegrationTest
  test '#show' do
    resume = resumes(:one)
    get resume_path(resume)
    assert_response :success
  end

  test '#index rss' do
    get resumes_path(format: :rss)
    assert_response :success
  end
end
