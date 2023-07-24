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

  test '#change_applying_state apply' do
    answer = resume_answers(:one)
    assert { answer.pending? }
    resume = answer.resume

    patch change_applying_state_resume_answer_path(resume, answer), params: { event: :apply }
    assert_response :redirect

    answer.reload

    assert { answer.applied? }
    assert { answer.user.notifications.find_by(kind: :answer_applied, resource: answer) }
  end

  test '#create' do
    resume = resumes(:full_without_answers)
    attrs = FactoryBot.attributes_for 'resume/answer'
    post resume_answers_path(resume), params: { resume_answer: attrs }

    # TODO: fix after update controller
    # https://github.com/Hexlet/hexlet-cv/blob/master/app/controllers/web/resumes/answers_controller.rb#L42
    assert_response :redirect

    answer = resume.answers.find_by attrs
    notification = Notification.find_by(user: resume.user, resource: answer, kind: :new_answer)
    event = Event.find_by(user: answer.user, resource: answer, kind: :new_answer)

    assert { event }
    assert { answer }
    assert { notification }
  end

  test '#create (validaton errors)' do
    resume = resumes(:full_without_answers)
    attrs = FactoryBot.attributes_for 'resume/answer', content: 'short'
    post resume_answers_path(resume), params: { resume_answer: attrs }
    assert_response :unprocessable_entity
  end

  test '#delete' do
    resume_answer = resume_answers(:one)

    delete resume_answer_path(resume_answer.resume, resume_answer)
    assert_response :redirect

    assert { !Resume::Answer.find_by(id: resume_answer.id) }
  end

  test 'new answer email' do
    resume = resumes(:full_without_answers)
    attrs = FactoryBot.attributes_for 'resume/answer'

    assert_emails 1 do
      post resume_answers_path(resume), params: { resume_answer: attrs }
    end
    assert_response :redirect
  end

  test 'author of the summary matches the author of the answer' do
    resume = resumes(:one_without_answer)
    attrs = FactoryBot.attributes_for 'resume/answer'

    assert_emails 0 do
      post resume_answers_path(resume), params: { resume_answer: attrs }
    end
    assert_response :redirect
  end
end
