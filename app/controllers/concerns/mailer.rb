# frozen_string_literal: true

module Mailer
  def send_new_answer_mail(answer)
    return nil unless answer.resume.user.can_send_resume_email?

    ResumeAnswerMailer.with(answer: answer).new_answer_email.deliver_later
  end
end
