# frozen_string_literal: true

class Web::Careers::MembersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @member = career_members(:member_one)
    @career = careers(:developer)
  end

  test '#show' do
    user = users(:one)
    sign_in(user)
    get career_member_path(@career.slug, @member)
    assert_response :success
  end

  test '#show user is not member' do
    user = users(:two)
    sign_in(user)
    get career_member_path(@career.slug, @member)

    assert_redirected_to root_path
  end
end
