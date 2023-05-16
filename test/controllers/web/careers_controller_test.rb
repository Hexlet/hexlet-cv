# frozen_string_literal: true

class Web::CareerControllerTest < ActionDispatch::IntegrationTest
  setup do
    user = users(:one)
    sign_in(user)
  end

  test '#index' do
    get careers_path
    assert_response :success
  end
end
