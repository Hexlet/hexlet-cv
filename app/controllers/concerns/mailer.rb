# frozen_string_literal: true

module Mailer
  def send_new_answer_mail(answer)
    ResumeAnswerMailer.with(answer: answer).new_answer_email.deliver_later if can_send_new_answer_mail?(answer)
  end

  private

  def can_send_new_answer_mail?(answer)
    answer.resume.user.can_send_email?
  end
end
