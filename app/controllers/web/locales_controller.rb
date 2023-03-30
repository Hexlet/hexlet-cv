# frozen_string_literal: true

module Web
  class Web::LocalesController < Web::ApplicationController
    skip_before_action :prepare_locale_settings, only: [:switch]

    def switch
      locale = params[:new_locale]

      unless I18n.available_locales.include?(locale&.to_sym)
        redirect_back fallback_location: root_path(locale: I18n.default_locale)
        return
      end

      unless current_or_guest_user.guest?
        current_user.locale = locale
        current_user.save!
      end

      session[:locale] = locale

      if locale&.to_sym == I18n.default_locale
        redirect_to root_path(locale:), allow_other_host: true
      else
        redirect_to root_path(locale: nil), allow_other_host: true
      end
    end
  end
end
