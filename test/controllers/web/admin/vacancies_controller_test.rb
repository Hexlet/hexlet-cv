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
    attrs = vacancy.attributes.merge state_event: :publish
    previous_published_at = vacancy.published_at
    patch admin_vacancy_path(vacancy), params: { vacancy: attrs }
    assert_response :redirect

    vacancy.reload

    assert { vacancy.published_at? }
    assert { vacancy.published_at != previous_published_at }
  end
end
