# frozen_string_literal: true

require 'test_helper'

class Web::VacanciesControllerTest < ActionDispatch::IntegrationTest
  test '#index' do
    get vacancies_path
    assert_response :success
  end

  test '#index rss' do
    get vacancies_path format: :rss
    assert_response :success
  end

  test '#show' do
    vacancy = vacancies(:one)
    get vacancy_path(vacancy)
    assert_response :success
  end

  test '#show vacancy without city name' do
    vacancy = vacancies(:without_city_name)
    get vacancy_path(vacancy)
    assert_response :success
  end

  test '#show json' do
    vacancy = vacancies(:one)
    get vacancy_path(vacancy, format: :json)
    assert_response :success
  end

  test '#index user anonimus' do
    user = users(:without_last_name_and_first_name)
    sign_in(user)

    get vacancies_path
    assert_redirected_to edit_account_profile_path
  end
end
