# frozen_string_literal: true

module Mailer
  def send_new_answer_mail(answer)
    resume = answer.resume
    user = resume.user
    return nil unless user.can_send_email? && user.resume_mail_enabled && !user.author?(answer)

    ResumeAnswerMailer.with(answer:).new_answer_email.deliver_later
  end
end
