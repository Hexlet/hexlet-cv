# frozen_string_literal: true

class Web::Careers::Steps::MembersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @career = careers(:developer)
  end

  test '#finish' do
    step = career_steps(:step_one)
    career_step_member = career_step_members(:one_by_user_full)
    user = users(:full)
    sign_in(user)

    patch finish_career_step_member_path(@career.slug, step, career_step_member)

    assert_response :redirect

    career_step_member.reload
    assert { career_step_member.finished? }
  end

  test '#finish last step' do
    user = users(:one)
    step = career_steps(:step_eight)
    career_step_member = career_step_members(:eight_by_user_one)
    career_member = career_members(:member_one)
    sign_in(user)

    patch finish_career_step_member_path(@career.slug, step, career_step_member)

    assert_response :redirect

    career_step_member.reload
    career_member = @career.members.find_by(user:)
    notification = Notification.find_by(kind: :career_member_finish, user:, resource: career_member)

    assert { notification }
    assert { career_step_member.finished? }
    assert { career_member.finished? }
  end

  test '#finish last steps email' do
    user = users(:one)
    step = career_steps(:step_eight)
    career_step_member = career_step_members(:eight_by_user_one)
    sign_in(user)

    assert_emails 1 do
      patch finish_career_step_member_path(@career.slug, step, career_step_member)
    end
  end

  test '#next step open source' do
    user = users(:next_step_open_source)
    step = career_steps(:step_one)
    career_step_member = career_step_members(:one_by_user_next_step_open_source)
    sign_in(user)

    assert_emails 1 do
      patch finish_career_step_member_path(@career.slug, step, career_step_member)
    end
  end
end
