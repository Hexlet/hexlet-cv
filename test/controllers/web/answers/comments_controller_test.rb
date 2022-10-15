# frozen_string_literal: true

require 'test_helper'

class Web::Answers::CommentsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:full)
    sign_in(@user)
  end

  test '#edit' do
    comment = resume_answer_comments(:two_one_full)
    answer = comment.answer

    get edit_answer_comment_path(answer, comment)
    assert_response :success
  end

  test '#update' do
    old_comment = resume_answer_comments(:two_one_full)
    answer = old_comment.answer
    attrs = FactoryBot.attributes_for 'resume/answer/comment'

    patch answer_comment_path(answer, old_comment), params: { resume_answer_comment: attrs }
    assert_response :redirect

    assert { answer.comments.find_by attrs }
  end

  test '#create' do
    answer = resume_answers(:full_one)
    attrs = FactoryBot.attributes_for('resume/answer/comment')
    post answer_comments_path(answer), params: { resume_answer_comment: attrs }
    assert_response :redirect

    comment = answer.comments.find_by attrs

    assert { comment }
    assert { Notification.find_by(user: answer.user, resource: comment, kind: :new_answer_comment) }
  end

  test '#create (nested comment)' do
    answer = resume_answers(:one)
    comment = resume_answer_comments(:one)
    attributes = {
      content: Faker::Lorem.sentence(word_count: 6)
    }

    assert_not(comment.has_children?)

    post answer_comments_path(answer), params: { resume_answer_comment: attributes.merge(parent_id: comment.id) }
    new_answer_comment = comment.children.find_by(attributes, user: @user)

    assert { new_answer_comment }
    assert { comment.has_children? }
    assert { Notification.find_by(user: comment.user, resource: new_answer_comment, kind: :new_answer_nested_comment) }
  end

  test '#create (has no notification for himself)' do
    answer = resume_answers(:one_full)
    attrs = FactoryBot.attributes_for('resume/answer/comment')
    post answer_comments_path(answer), params: { resume_answer_comment: attrs }
    assert_response :redirect

    comment = answer.comments.find_by attrs

    assert { !Notification.find_by(user: answer.user, resource: comment, kind: :new_answer_comment) }
  end

  test '#create (invalid comment)' do
    answer = resume_answers(:full_one)
    attrs = FactoryBot.attributes_for('resume/answer/comment', content: 'short')
    post answer_comments_path(answer), params: { resume_answer_comment: attrs }
    assert_response :success
  end

  test '#destroy' do
    comment = resume_answer_comments(:two_one_full)
    delete answer_comment_path(comment.answer, comment)
    assert_response :redirect
    assert { !Notification.find_by(resource: comment) }
  end
end
