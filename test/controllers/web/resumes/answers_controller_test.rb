# frozen_string_literal: true

require 'test_helper'

class Web::Resumes::AnswersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#edit' do
    resume_answer = resume_answers(:one)
    resume = resume_answer.resume

    get edit_resume_answer_path(resume_answer, resume)
    assert_response :success
  end

  test '#update' do
    old_answer = resume_answers(:one)
    resume = old_answer.resume
    attrs = FactoryBot.attributes_for 'resume/answer'

    patch resume_answer_path(old_answer, resume), params: { resume_answer: attrs }
    assert_response :redirect

    assert { resume.answers.find_by! attrs }
  end

  test '#create' do
    resume = resumes(:full_without_answers)
    attrs = FactoryBot.attributes_for 'resume/answer'
    post resume_answers_path(resume), params: { resume_answer: attrs }
    assert_response :redirect

    answer = resume.answers.find_by! attrs
    assert { answer }
  end

  test '#create (validaton errors)' do
    resume = resumes(:full_without_answers)
    attrs = FactoryBot.attributes_for 'resume/answer', content: 'short'
    post resume_answers_path(resume), params: { resume_answer: attrs }
    assert_response :success
  end

  test '#delete' do
    resume_answer = resume_answers(:one)

    delete resume_answer_path(resume_answer.resume, resume_answer)
    assert_response :redirect

    assert { !Resume::Answer.exists?(resume_answer.id) }
  end
end
