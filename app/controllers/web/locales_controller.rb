# frozen_string_literal: true

module Web
  class LocalesController < Web::ApplicationController
    skip_before_action :prepare_locale_settings, only: [:switch]

    def switch
      locale = params[:new_locale]
      # redirect_path = requst.referer || root_path

      unless I18n.available_locales.include?(locale&.to_sym)
        redirect_back fallback_location: root_path(locale: I18n.default_locale)
        return
      end

      session[:locale] = locale

      if locale&.to_sym == I18n.default_locale # en == en / перключаем на en
        redirect_to root_path(locale: nil), allow_other_host: true
      else
        redirect_to root_path(locale:), allow_other_host: true
      end
    end
  end
end
