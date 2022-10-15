# frozen_string_literal: true

require 'test_helper'

class ResumeNestedCommentMailerTest < ActionMailer::TestCase
  test 'new nested comment' do
    comment = resume_comments(:one)
    user = comment.user
    mail = ResumeNestedCommentMailer.with(comment:, user:).new_comment_email
    assert { user.email == mail.to.first }
  end
end
