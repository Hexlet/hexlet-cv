# frozen_string_literal: true

# Preview all emails at http://localhost:3000/rails/mailers/answer_comment_mailer
class AnswerCommentMailerPreview < ActionMailer::Preview
  def new_comment_email
    AnswerCommentMailer.with(comment: Resume::Answer::Comment.first).new_comment_email
  end
end
