# frozen_string_literal: true

class Web::Careers::MembersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @member = career_members(:one)
    @career = careers(:one)
  end

  test '#show' do
    user = users(:one)
    sign_in(user)
    get career_member_path(@career, @member)
    assert_response :success
  end

  test '#show user is not member' do
    user = users(:two)
    sign_in(user)
    get career_member_path(@career, @member)

    assert_redirected_to root_path
  end
end
