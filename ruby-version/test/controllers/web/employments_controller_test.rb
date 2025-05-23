# frozen_string_literal: true

require 'test_helper'

class Web::EmploymentsControllerTest < ActionDispatch::IntegrationTest
  test '#show' do
    get employment_url
    assert_response :success
  end

  test '#user anonimus' do
    user = users(:without_last_name_and_first_name)
    sign_in(user)

    get employment_url
    assert_redirected_to edit_account_profile_path
  end
end
