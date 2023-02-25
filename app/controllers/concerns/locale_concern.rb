# frozen_string_literal: true

module LocaleConcern
  extend ActiveSupport::Concern

  included do
    around_action :switch_locale
  end

  def switch_locale(&)
    locale = params[:locale]&.present? ? extract_locale : :en
    session[:locale] = extract_locale_from_path if session[:locale].nil?
    I18n.with_locale(locale, &)
  end

  def extract_locale
    locale = params[:locale]
    return locale if I18n.available_locales.map(&:to_s).include?(locale)

    nil
  end

  def extract_locale_from_path
    parsed_locale = request.path.scan(%r{/en|/ru})&.first&.split('/')&.last
    if parsed_locale.nil? || parsed_locale == 'en'
      :en
    else
      :ru
    end
  end
end
