# frozen_string_literal: true

# Preview all emails at http://localhost:3000/rails/mailers/answer_nested_comment_mailer
class AnswerNestedCommentMailerPreview < ActionMailer::Preview
  def new_comment_email
    AnswerNestedCommentMailer.with(comment: Resume::Answer::Comment.first, user: User.last).new_comment_email
  end
end
