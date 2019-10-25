# frozen_string_literal: true

module Mailer
  def new_answer_mailer(answer)
    ResumeAnswerMailer.with(answer: answer).new_answer_email.deliver_later if can_send_new_answer_mail?(answer)
  end

  private

  def can_send_new_answer_mail?(answer)
    user = answer.resume.user
    user.resume_mailer && !user.bounced_email && !user.marked_as_spam && !user.unconfirmed_email
  end
end
