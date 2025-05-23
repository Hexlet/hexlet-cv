# frozen_string_literal: true

# Preview all emails at http://localhost:3000/rails/mailers/resume_answer_mailer
class ResumeAnswerMailerPreview < ActionMailer::Preview
  def new_answer_email
    ResumeAnswerMailer.with(answer: Resume::Answer.first).new_answer_email
  end
end
