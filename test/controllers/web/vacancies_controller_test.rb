# frozen_string_literal: true

require 'test_helper'

class Web::VacanciesControllerTest < ActionDispatch::IntegrationTest
  test '#index' do
    get vacancies_path
    assert_response :success
  end

  test '#show' do
    vacancy = vacancies(:one)
    get vacancy_path(vacancy)
    assert_response :success
  end
end
