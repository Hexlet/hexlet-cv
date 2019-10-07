# frozen_string_literal: true

require 'test_helper'

class Web::Answers::LikesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:full)
    sign_in(@user)
  end

  test '#create' do
    answer = resume_answers(:without_likes)
    post answer_likes_path(answer)
    assert_response :redirect

    like = answer.likes.find_by user: @user

    assert { Notification.find_by(user: answer.user, resource: like, kind: :new_answer_like) }
  end

  test '#destroy' do
    like = resume_answer_likes(:full_full_two)
    delete answer_like_path(like.answer, like)
    assert_response :redirect

    assert { !Notification.find_by(resource: like) }
  end

  test '#create (cannot like himself)' do
    answer = resume_answers(:one_full)
    post answer_likes_path(answer)
    assert_response :redirect

    assert { !answer.likes.find_by(user: @user) }
  end
end
