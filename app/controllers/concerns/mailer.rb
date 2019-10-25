# frozen_string_literal: true

module Mailer
  def new_answer_mailer(answer)
    ResumeAnswerMailer.with(answer: answer).new_answer_email.deliver_later if can_send_new_answer_mail?(answer)
  end

  private

  def can_send_new_answer_mail?(answer)
    answer.resume.user.resume_mailer && !answer.resume.user.bounced_email && !answer.resume.user.marked_as_spam && !answer.resume.user.unconfirmed_email
  end
end
