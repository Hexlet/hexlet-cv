# frozen_string_literal: true

class Web::CareerControllerTest < ActionDispatch::IntegrationTest
  setup do
    user = users(:one)
    sign_in(user)
  end

  test '#show' do
    career = careers(:developer)
    get career_path(career.slug)

    assert_response :success
  end
end
