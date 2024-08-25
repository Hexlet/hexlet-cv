# frozen_string_literal: true

require 'test_helper'

class Web::Admin::VacanciesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @admin = users(:admin)
    sign_in(@admin)
  end

  test '#index' do
    get admin_vacancies_path
    assert_response :success
  end

  test '#index export csv' do
    get admin_vacancies_path(format: :csv)
    assert_response :success
  end

  test '#new' do
    get new_admin_vacancy_path

    assert_response :success
  end

  test '#create' do
    attrs = FactoryBot.attributes_for :vacancy

    post admin_vacancies_path, params: { vacancy: attrs }

    vacancy = Vacancy.find_by(title: attrs[:title])
    assert_redirected_to edit_admin_vacancy_path(vacancy)
    assert { vacancy }
    assert { vacancy.idle? }
  end

  test '#edit' do
    vacancy = vacancies(:one)
    get edit_admin_vacancy_path(vacancy)
    assert_response :success
  end

  test '#update' do
    vacancy = vacancies(:one)
    attrs = FactoryBot.attributes_for :vacancy

    patch admin_vacancy_path(vacancy), params: { vacancy: attrs }
    assert_response :redirect

    vacancy.reload

    assert { vacancy.title == attrs[:title] }
  end

  test '#publish' do
    vacancy = vacancies(:archived)
    state_event = :publish
    attrs = vacancy.attributes.merge(state_event:)
    previous_published_at = vacancy.published_at
    patch admin_vacancy_path(vacancy), params: { vacancy: attrs }
    assert_response :redirect

    vacancy.reload
    notification = Notification.find_by(resource: vacancy, kind: "vacancy_#{state_event}")

    assert { notification }
    assert { vacancy.published_at? }
    assert { vacancy.published_at != previous_published_at }
  end

  test '#export vacancies to csv' do
    get admin_vacancies_path(format: :csv)
    assert_response :success
  end

  test '#on_moderate' do
    get on_moderate_admin_vacancies_path
    assert_response :success
  end

  test '#archive' do
    vacancy = vacancies(:one)

    patch archive_admin_vacancy_path(vacancy), params: { go_to: admin_vacancies_path(locale: I18n.locale) }

    assert_redirected_to admin_vacancies_path(locale: I18n.locale)
    vacancy.reload

    assert { vacancy.archived? }
  end

  test '#restore' do
    vacancy = vacancies(:archived)

    patch restore_admin_vacancy_path(vacancy), params: { go_to: admin_vacancies_path(locale: I18n.locale) }

    assert_redirected_to admin_vacancies_path(locale: I18n.locale)
    vacancy.reload

    assert { vacancy.on_moderate? }
  end

  test '#cancele' do
    vacancy = vacancies(:on_moderate)
    go_to = on_moderate_admin_vacancies_path
    attrs = vacancy.attributes.merge(cancelation_reason: :high_requirements)

    patch cancel_admin_vacancy_path(vacancy), params: { vacancy: attrs, go_to: }

    assert_redirected_to go_to
    vacancy.reload
    notification = Notification.find_by(resource: vacancy, kind: :vacancy_cancel)

    assert { notification }
    assert { vacancy.canceled? }
  end

  test '#cancele with invalid params' do
    vacancy = vacancies(:on_moderate)
    attrs = vacancy.attributes

    patch cancel_admin_vacancy_path(vacancy), params: { vacancy: attrs }

    assert_response :unprocessable_entity
    vacancy.reload
    notification = Notification.find_by(resource: vacancy, kind: :vacancy_cancel)

    assert { !notification }
    assert { vacancy.on_moderate? }
  end

  test '#new_cancelation_reason' do
    vacancy = vacancies(:on_moderate)

    get new_cancelation_admin_vacancy_path(vacancy)

    assert_response :success
  end
end
