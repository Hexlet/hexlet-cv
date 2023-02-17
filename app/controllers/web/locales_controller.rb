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

      session[:locale] = locale

      friendly_redirect(request.referer, locale)
    end

    private

    def friendly_redirect(url, locale)
      parsed_url = URI.parse(url || root_path)
      if locale&.to_sym == I18n.default_locale
        path = parsed_url.path == '/' ? "/#{locale}" : "/#{locale}#{parsed_url.path}"
        parsed_url.path = path
      else
        parsed_url.path = parsed_url.path.sub('/ru', '')
      end
      redirect_to url_for(parsed_url.to_s), allow_other_host: true
    end
  end
end
