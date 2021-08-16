# frozen_string_literal: true

module VacancyPresenter
  include ActionView::Helpers::NumberHelper

  # def combined_location
  #   city_name? ? [location, city_name].compact_blank.join(', ') : I18n.t('remote_job')
  # end

  def header
    "#{position_level.text} #{title} (#{employment_type.text}) – #{company_name}"
  end

  def salary
    return nil if !salary_to? && !salary_from?

    # from = number_to_currency salary_from, locale: :ru, precision: 0, format: '%n'
    # to = number_to_currency salary_to, locale: :ru, precision: 0, format: '%n'
    from = salary_from? && I18n.t('salary.from', value: number_with_delimiter(salary_from))
    to = salary_to? && I18n.t('salary.to', value: number_with_delimiter(salary_to))

    value = [from, to].compact_blank.join ' '
    "#{value} #{salary_currency.text} (#{salary_amount_type.text})"
  end
end
