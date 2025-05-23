# frozen_string_literal: true

require 'test_helper'

class Web::VacancyFiltersControllerTest < ActionDispatch::IntegrationTest
  test 'should get index' do
    get vacancy_filter_url('tag-php')
    assert_response :success
  end

  test '#index 2' do
    get vacancy_filter_url('city-moskwa_level-junior_technology-android')
    assert_response :success
  end

  test '#index 3' do
    get vacancy_filter_url('city-kislowodsk _level-middle_technology-php / symfony')
    assert_response :success
  end

  test 'search' do
    params = { level: :junior }
    get search_vacancy_filters_url(params: { web_vacancies_search_form: params })

    assert_response :redirect
  end
end
