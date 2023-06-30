# frozen_string_literal: true

require 'test_helper'

class Web::Admin::CareerMemberUsersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @admin = users(:admin)
    sign_in(@admin)
  end

  test '#new' do
    get new_admin_career_member_user_path

    assert_response :success
  end

  test '#create' do
    career = careers(:analytics)
    user = users(:two)

    attrs = {
      career_id: career.id,
      user_id: user.id
    }

    post admin_career_member_users_path, params: { career_member: attrs }

    assert_redirected_to admin_career_users_path

    career_member = Career::Member.find_by(attrs)

    assert { career_member }
  end
end
