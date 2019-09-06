require 'test_helper'

class Web::Answers::LikesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#create' do
    answer = resume_answers(:one)
    post answer_likes_path(answer)
    assert_response :redirect
  end

  test '#destroy' do
    like = resume_answer_likes(:one)
    post answer_likes_path(like.answer, like)
    assert_response :redirect
  end
end
