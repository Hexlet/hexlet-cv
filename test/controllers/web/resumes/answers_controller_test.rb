# frozen_string_literal: true

require 'test_helper'

class Web::Resumes::AnswersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#create' do
    resume = resumes(:one)
    attrs = {
      content: 'some name'
    }
    post resume_answers_path(resume), params: { resume_answer: attrs }
    assert_response :redirect

    answer = resume.answers.find_by! attrs
    assert answer
  end
end
