# frozen_string_literal: true

require 'test_helper'

class Web::Answers::CommentsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#create' do
    answer = resume_answers(:full_one)
    attrs = FactoryBot.attributes_for('resume/answer/comment')
    post answer_comments_path(answer), params: { resume_answer_comment: attrs }
    assert_response :redirect

    assert { answer.comments.exists?(attrs) }
  end

  test '#create (invalid comment)' do
    answer = resume_answers(:full_one)
    attrs = FactoryBot.attributes_for('resume/answer/comment', content: 'short')
    post answer_comments_path(answer), params: { resume_answer_comment: attrs }
    assert_response :success
  end

  test '#destroy' do
    comment = resume_answer_comments(:one)
    delete answer_comment_path(comment.answer, comment)
    assert_response :redirect
  end
end
