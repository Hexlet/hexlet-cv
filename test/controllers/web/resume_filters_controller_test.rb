# frozen_string_literal: true

require 'test_helper'

class Web::ResumeFiltersControllerTest < ActionDispatch::IntegrationTest
  test 'should get index' do
    get resume_filter_url('tag-php')
    assert_response :success
  end

  test 'should search' do
    params = { level: :junior }
    get search_resume_filters_url(params: { web_resume_search_form: params })

    assert_response :redirect
  end
end
