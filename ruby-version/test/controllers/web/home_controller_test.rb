# frozen_string_literal: true

require 'test_helper'

class Web::HomeControllerTest < ActionDispatch::IntegrationTest
  test '#index' do
    get root_url
    assert_response :success
  end

  test '#user anonimus' do
    user = users(:without_last_name_and_first_name)
    sign_in(user)

    get root_url
    assert_redirected_to edit_account_profile_path
  end
end
