# frozen_string_literal: true

require 'test_helper'

class Web::Account::VacanciesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:full)
    sign_in(@user)
  end

  test '#index' do
    get account_vacancies_path
    assert_response :success
  end

  test '#new' do
    get new_account_vacancy_path
    assert_response :success
  end

  test '#create' do
    attrs = FactoryBot.attributes_for :vacancy

    post account_vacancies_path, params: { vacancy: attrs }
    assert_response :redirect

    vacancy = Vacancy.find_by(title: attrs[:title])
    assert { vacancy }
  end

  test '#edit' do
    vacancy = vacancies(:one)
    get edit_account_vacancy_path(vacancy)
    assert_response :success
  end

  test '#update' do
    attrs = FactoryBot.attributes_for :vacancy
    vacancy = vacancies(:one)

    patch account_vacancy_path(vacancy), params: { vacancy: attrs }
    assert_response :redirect

    vacancy.reload

    assert { vacancy.title == attrs[:title] }
  end
end
