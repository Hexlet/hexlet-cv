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

  test '#show' do
    career = careers(:one)
    get career_path(career)
    assert_response :success
  end
end
