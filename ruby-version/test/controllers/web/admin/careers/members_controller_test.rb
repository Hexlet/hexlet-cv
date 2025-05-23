# frozen_string_literal: true

require 'test_helper'

class Web::Admin::Careers::MembersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @admin = users(:admin)
    @user = users(:two)
    @career = careers(:devops)
    sign_in(@admin)
  end

  test '#create' do
    attrs = {
      user_id: @user.id
    }

    post admin_career_members_path(@career), params: { career_member: attrs }
    assert_redirected_to admin_career_path(@career)

    member = Career::Member.find_by(user_id: attrs[:user_id])
    notification = Notification.find_by(user: @user, resource: member, kind: :new_career_member)
    event = Event.find_by(user: @user, resource: member, kind: :new_career_member)
    version = Career::Member::Version.find_by(item: member, item_type: 'Career::Member', event: 'activate')

    assert { member }
    assert { event }
    assert { notification }
    assert { version }
  end

  test '#archive' do
    member = career_members(:member_one)

    assert_difference -> { member.reload.versions.size } => 1 do
      patch archive_admin_career_member_path(@career, member)
    end

    assert_redirected_to admin_career_path(@career)
    member.reload
    version = member.versions.find_by(item_type: 'Career::Member', event: 'archive')

    assert { member.archived? }
    assert { version }
  end

  test '#activate' do
    member = career_members(:archived_member)

    assert_difference -> { member.reload.versions.size } => 1 do
      patch activate_admin_career_member_path(@career, member)
    end

    assert_redirected_to admin_career_member_users_path
    member.reload
    version = member.versions.find_by(item_type: 'Career::Member', event: 'activate')

    assert { member.active? }
    assert { version }
  end

  test 'new career member email' do
    attrs = {
      user_id: @user.id
    }

    post admin_career_members_path(@career), params: { career_member: attrs }

    member = Career::Member.find_by(user_id: attrs[:user_id])
    event = Event.find_by(user: @user, resource: member, kind: :new_career_member)

    assert { event.sended? }
  end

  test 'with user at disabled email delivery' do
    user = users(:mail_delivery_disabled)
    attrs = {
      user_id: user.id
    }

    post admin_career_members_path(@career), params: { career_member: attrs }

    member = Career::Member.find_by(user_id: attrs[:user_id])
    event = Event.find_by(user:, resource: member, kind: :new_career_member)

    assert { event.unsended? }
  end
end
