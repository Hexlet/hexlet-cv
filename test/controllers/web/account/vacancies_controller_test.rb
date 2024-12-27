# frozen_string_literal: true

require 'test_helper'

class Web::Account::VacanciesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @hr = users(:full)
    sign_in(@hr)
  end

  test '#index' do
    get account_vacancies_path
    assert_response :success
  end

  test '#index user anonimus' do
    user = users(:without_last_name_and_first_name)
    sign_in(user)

    get account_vacancies_path
    assert_redirected_to edit_account_profile_path
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

  test 'can not edit a posted job' do
    user = users(:two)
    published_vacancy = vacancies(:two)
    sign_out @hr
    sign_in user

    get edit_account_vacancy_path(published_vacancy)

    assert_redirected_to root_path
  end

  test 'can not edit a archived job' do
    user = users(:one)
    archived_vacancy = vacancies(:archived_one)
    sign_out @hr
    sign_in user

    get edit_account_vacancy_path(archived_vacancy)

    assert_redirected_to root_path
  end

  test 'can not update archived job' do
    attrs = FactoryBot.attributes_for :vacancy
    user = users(:one)
    archived_vacancy = vacancies(:archived_one)
    sign_out @hr
    sign_in user

    patch account_vacancy_path(archived_vacancy), params: { vacancy: attrs }
    assert_response :redirect

    archived_vacancy.reload

    assert { archived_vacancy.title != attrs[:title] }
  end

  test '#update' do
    attrs = FactoryBot.attributes_for :vacancy
    vacancy = vacancies(:one)

    patch account_vacancy_path(vacancy), params: { vacancy: attrs }
    assert_response :redirect

    vacancy.reload

    assert { vacancy.title == attrs[:title] }
  end

  test 'can not update a posted job' do
    user = users(:two)
    sign_out @hr
    sign_in user
    attrs = FactoryBot.attributes_for :vacancy
    published_vacancy = vacancies(:two)

    patch account_vacancy_path(published_vacancy), params: { vacancy: attrs }
    assert_redirected_to root_path

    published_vacancy.reload

    assert { published_vacancy.title != attrs[:title] }
  end

  test '#send_to_moderate' do
    vacancy = vacancies(:draft_full)
    attrs = FactoryBot.attributes_for(:vacancy)
    params = {
      on_moderate: true,
      vacancy: attrs
    }

    patch(account_vacancy_path(vacancy), params:)
    vacancy.reload

    assert_redirected_to account_vacancies_path
    assert { vacancy.on_moderate? }
  end
end
