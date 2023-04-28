# frozen_string_literal: true

class Web::Careers::Steps::MembersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @career = careers(:one)
    @step = career_steps(:one)
    @career_step_member = career_step_members(:one)
  end

  test '#finish' do
    user = users(:one)
    sign_in(user)

    patch finish_career_step_member_path(@career, @step, @career_step_member)

    assert_response :redirect

    @career_step_member.reload
    assert { @career_step_member.finished? }
  end
end
