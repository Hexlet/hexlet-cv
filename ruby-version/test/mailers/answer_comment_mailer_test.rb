# frozen_string_literal: true

require 'test_helper'

class AnswerCommentMailerTest < ActionMailer::TestCase
  test 'new comment' do
    comment = resume_answer_comments(:one)
    mail = AnswerCommentMailer.with(comment:).new_comment_email
    assert { comment.answer.user.email == mail.to.first }
  end
end
