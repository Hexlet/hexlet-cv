# frozen_string_literal: true

module VacancyPresenter
  def location
    city_name? ? [city_name, country].join(', ') : I18n.t('remote_job')
  end
end
