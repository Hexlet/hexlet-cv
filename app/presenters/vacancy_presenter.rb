# frozen_string_literal: true

module VacancyPresenter
  include ActionView::Helpers::NumberHelper

  def combined_location
    city_name? ? [location, city_name, country].compact.join(', ') : I18n.t('remote_job')
  end

  def salary
    from = number_to_currency salary_from, locale: :ru, precision: 0, format: '%n'
    to = number_to_currency salary_to, locale: :ru, precision: 0
    [from, to].compact.join ' – '
  end
end
