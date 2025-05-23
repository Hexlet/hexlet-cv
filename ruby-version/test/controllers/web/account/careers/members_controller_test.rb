# frozen_string_literal: true

class Web::Account::Careers::MembersControllerTest < ActionDispatch::IntegrationTest
  test '#index' do
    user = users(:full)
    sign_in(user)

    get account_members_path

    assert_response :success
  end
end
