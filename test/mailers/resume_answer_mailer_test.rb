# frozen_string_literal: true

require 'test_helper'

class ResumeAnswerMailerTest < ActionMailer::TestCase
  test 'new answer' do
    answer = resume_answers(:one)
    mail = ResumeAnswerMailer.with(answer:).new_answer_email
    assert { answer.resume.user.email == mail.to.first }
  end
end
