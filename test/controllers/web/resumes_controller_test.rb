# frozen_string_literal: true

require 'test_helper'

class Web::ResumesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @resume = resumes(:one)
  end

  test '#show' do
    get resume_path(@resume, locale: I18n.locale)
    assert_response :success
  end

  test '#show from boot' do
    @resume.destroy
    get resume_path(@resume, locale: I18n.locale), headers: { 'User-Agent': 'Bot' }
    assert_redirected_to root_path
  end

  test '#index rss' do
    get resumes_path(format: :rss)
    assert_response :success
  end

  test '#user anonimus' do
    user = users(:without_last_name_and_first_name)
    sign_in(user)

    get resume_path(@resume, locale: I18n.locale)
    assert_redirected_to edit_account_profile_path
  end
end
