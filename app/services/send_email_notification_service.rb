# frozen_string_literal: true

class SendEmailNotificationService
  class << self
    def send_new_answer_mail(answer)
      user = answer.resume.user
      return nil unless can_send_email_notification?(user, answer)

      ResumeAnswerMailer.with(answer:).new_answer_email.deliver_later
    end

    def send_new_comment_to_resume_email(comment)
      user = comment.resume.user
      return nil unless can_send_email_notification?(user, comment)

      ResumeCommentMailer.with(comment:).new_comment_email.deliver_later
    end

    def send_new_comment_to_answer_email(comment)
      user = comment.answer.user
      return nil unless can_send_email_notification?(user, comment)

      AnswerCommentMailer.with(comment:).new_comment_email.deliver_later
    end

    private

    def can_send_email_notification?(user, object)
      user.can_send_email? && user.resume_mail_enabled && !user.author?(object)
    end
  end
end
