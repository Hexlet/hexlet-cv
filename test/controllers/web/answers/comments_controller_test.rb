require 'test_helper'

class Web::Answers::CommentsControllerTest < ActionDispatch::IntegrationTest
  test "should get new" do
    get web_answers_comments_new_url
    assert_response :success
  end

  test "should get edit" do
    get web_answers_comments_edit_url
    assert_response :success
  end

end
