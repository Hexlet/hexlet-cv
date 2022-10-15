# frozen_string_literal: true

require 'test_helper'

class AnswerNestedCommentMailerTest < ActionMailer::TestCase
  test 'create new answer nested comment' do
    comment = resume_answer_comments(:one)
    user = comment.user
    mail = AnswerNestedCommentMailer.with(comment:, user:).new_comment_email

    assert { user.email == mail.to.first }
  end
end
