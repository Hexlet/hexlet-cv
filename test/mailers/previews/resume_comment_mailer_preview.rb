# frozen_string_literal: true

# Preview all emails at http://localhost:3000/rails/mailers/resume_comment_mailer
class ResumeCommentMailerPreview < ActionMailer::Preview
  def new_comment_email
    ResumeCommentMailer.with(comment: Resume::Comment.first).new_comment_email
  end
end
