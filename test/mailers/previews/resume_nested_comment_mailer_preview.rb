# frozen_string_literal: true

# Preview all emails at http://localhost:3000/rails/mailers/resume_nested_comment_mailer
class ResumeNestedCommentMailerPreview < ActionMailer::Preview
  def new_comment_email
    ResumeNestedCommentMailer.with(comment: Resume::Comment.first, user: User.last).new_comment_email
  end
end
