require 'test_helper'

class Web::ResumesControllerTest < ActionDispatch::IntegrationTest
  test "should get show" do
    get web_resumes_show_url
    assert_response :success
  end

end
