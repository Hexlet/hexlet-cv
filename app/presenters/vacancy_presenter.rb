# frozen_string_literal: true

module VacancyPresenter
  include ActionView::Helpers::NumberHelper

  def combined_location
    city_name? ? [location, city_name, country].compact_blank.join(', ') : I18n.t('remote_job')
  end

  def salary
    return nil if !salary_to? && !salary_from?

    # from = number_to_currency salary_from, locale: :ru, precision: 0, format: '%n'
    # to = number_to_currency salary_to, locale: :ru, precision: 0, format: '%n'
    from = number_with_delimiter salary_from
    to = number_with_delimiter salary_to

    value = [from, to].compact_blank.join ' – '
    "#{value} #{I18n.t('number.currency.format.unit')}"
  end
end
