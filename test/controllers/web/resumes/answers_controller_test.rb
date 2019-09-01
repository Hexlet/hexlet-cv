require 'test_helper'

class Web::Resumes::AnswersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    @session = login(@user)
  end

  test '#create' do
    resume = resumes(:one)
    attrs = {
      content: 'some name'
    }
    @session.post resume_answers_path(resume), params: { resume_answer: attrs }
    @session.assert_response :redirect

    answer = resume.answers.find_by! attrs
    assert answer
  end
end
