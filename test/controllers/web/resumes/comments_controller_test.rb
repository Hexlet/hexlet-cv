# frozen_string_literal: true

require 'test_helper'

class Web::Resumes::CommentsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#edit' do
    resume_comment = resume_comments(:one)
    resume = resume_comment.resume

    get edit_resume_comment_path(resume_comment, resume)
    assert_response :success
  end

  test '#update' do
    old_comment = resume_comments(:one)
    resume = old_comment.resume
    attrs = FactoryBot.attributes_for 'resume/comment'

    patch resume_comment_path(old_comment, resume), params: { resume_comment: attrs }
    assert_response :redirect

    assert { resume.comments.find_by! attrs }
  end

  test '#create' do
    resume = resumes(:one)
    attrs = FactoryBot.attributes_for('resume/comment')
    post resume_comments_path(resume), params: { resume_comment: attrs }
    assert_response :redirect

    assert { resume.comments.exists?(attrs) }

    resume_owner = resume.user
    assert { Notification.exists?(user: resume_owner, kind: :new_comment) }
  end

  test '#create (invalid comment)' do
    resume = resumes(:one)
    attrs = FactoryBot.attributes_for('resume/comment', content: 'short')
    post resume_comments_path(resume), params: { resume_comment: attrs }
    assert_response :success
  end

  test '#destroy' do
    comment = resume_comments(:two)
    delete resume_comment_path(comment.resume, comment)
    assert_response :redirect
  end
end
