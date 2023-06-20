# frozen_string_literal: true

require 'test_helper'

class Web::Admin::CareerMemberUsersControllerTest < ActionDispatch::IntegrationTest
  test '#create' do
    career = careers(:analytics)
    user = users(:one)
    admin = users(:admin)
    sign_in(admin)

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
