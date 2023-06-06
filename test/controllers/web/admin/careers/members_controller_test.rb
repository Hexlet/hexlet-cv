# frozen_string_literal: true

require 'test_helper'

class Web::Admin::Careers::MembersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @admin = users(:admin)
    sign_in(@admin)
  end

  test '#create' do
    user = users(:two)
    career = careers(:devops)
    attrs = {
      user_id: user.id
    }

    post admin_career_members_path(career), params: { career_member: attrs }
    assert_redirected_to admin_career_path(career)

    member = Career::Member.find_by(user_id: attrs[:user_id])
    assert { member }
  end

  test '#archive' do
    member = career_members(:member_one)
    career = careers(:devops)

    patch archive_admin_career_member_path(career, member)

    assert_redirected_to admin_career_path(career)
    member.reload
    assert { member.archived? }
  end
end
