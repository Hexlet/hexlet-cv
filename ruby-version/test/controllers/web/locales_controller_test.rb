# frozen_string_literal: true

class Web::LocalesControllerTest < ActionDispatch::IntegrationTest
  test 'switch to en' do
    get switch_locale_url(locale: nil), params: { new_locale: :en }

    assert_redirected_to root_path(locale: nil)
  end

  test 'switch to ru' do
    get switch_locale_url(locale: nil), params: { new_locale: :ru }

    assert_redirected_to root_path(locale: :ru)
  end

  test 'switch to unavailable locale' do
    get switch_locale_url, params: { new_locale: 'wrong' }
    assert_response :redirect
  end
end
