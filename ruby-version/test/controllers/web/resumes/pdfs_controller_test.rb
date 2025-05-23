# frozen_string_literal: true

require 'test_helper'

class Web::Resumes::PdfsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    @resume = resumes(:one)
    sign_in(@user)
  end

  test '#show base template' do
    template = 'base'
    get resume_pdf_path(@resume, template)
    assert_response :success
  end

  test '#show undefined template' do
    assert_raise ActionController::RoutingError do
      template = 'undefined'
      get resume_pdf_path(@resume, template)
    end
  end
end
