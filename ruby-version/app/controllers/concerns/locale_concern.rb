# frozen_string_literal: true

module LocaleConcern
  extend ActiveSupport::Concern

  included do
    around_action :switch_locale
  end

  def switch_locale(&)
    locale = params[:locale].present? ? extract_locale : :en
    I18n.with_locale(locale, &)
  end

  def extract_locale
    locale = params[:locale]
    return locale if I18n.available_locales.map(&:to_s).include?(locale)

    nil
  end
end
