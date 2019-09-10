# frozen_string_literal: true

require 'test_helper'

class Web::Resumes::CommentsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#create' do
    resume = resumes(:one)
    attrs = FactoryBot.attributes_for('resume/comment')
    post resume_comments_path(resume), params: { resume_comment: attrs }
    assert_response :redirect

    assert resume.comments.exists?(attrs)
  end

  test '#destroy' do
    comment = resume_comments(:two)
    delete resume_comment_path(comment.resume, comment)
    assert_response :redirect
  end
end
