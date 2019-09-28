# frozen_string_literal: true

require 'test_helper'

class Web::Answers::LikesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#create' do
    answer = resume_answers(:without_likes)
    post answer_likes_path(answer)
    assert_response :redirect

    answer_owner = answer.user
    assert { Notification.exists?(user: answer_owner, kind: :new_answer_like) }
  end

  test '#destroy' do
    like = resume_answer_likes(:one)
    delete answer_like_path(like.answer, like)
    assert_response :redirect
  end
end
