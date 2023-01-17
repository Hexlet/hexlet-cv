# frozen_string_literal: true

require 'test_helper'

class Web::EmploymentsControllerTest < ActionDispatch::IntegrationTest
  test '#show' do
    get employment_url
    assert_response :success
  end
end
