# frozen_string_literal: true

class EmailSender
  class << self
    def send_new_answer_mail(answer)
      user = answer.resume.user
      return unless can_send_email_notification?(user, answer)

      ResumeAnswerMailer.with(answer:).new_answer_email.deliver_later
    end

    def send_new_comment_to_resume_email(comment)
      user = comment.resume.user
      return unless can_send_email_notification?(user, comment)

      ResumeCommentMailer.with(comment:).new_comment_email.deliver_later
    end

    def send_new_comment_to_answer_email(comment)
      user = comment.answer.user
      return unless can_send_email_notification?(user, comment)

      AnswerCommentMailer.with(comment:).new_comment_email.deliver_later
    end

    def send_new_career_member_email(career_member)
      user = career_member.user

      return unless user.can_send_email?

      CareerMemberMailer.with(career_member:, user:).new_career_member_email.deliver_later
    end

    private

    def can_send_email_notification?(user, resource)
      user.can_send_email? && !resource.author?(user)
    end
  end
end
