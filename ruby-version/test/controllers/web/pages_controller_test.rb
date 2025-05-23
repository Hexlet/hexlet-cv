# frozen_string_literal: true

require 'test_helper'

class Web::PagesControllerTest < ActionDispatch::IntegrationTest
  test 'should get about' do
    get page_path('about')
    assert_response :success
  end

  test '#user anonimus' do
    user = users(:without_last_name_and_first_name)
    sign_in(user)

    get page_path('about')
    assert_redirected_to edit_account_profile_path
  end
end
