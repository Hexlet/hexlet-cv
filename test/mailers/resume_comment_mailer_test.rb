# frozen_string_literal: true

require 'test_helper'

class ResumeCommentMailerTest < ActionMailer::TestCase
  test 'new comment' do
    comment = resume_comments(:one)
    mail = ResumeCommentMailer.with(comment:).new_comment_email
    assert { comment.resume.user.email == mail.to.first }
  end
end
