# frozen_string_literal: true

class Web::LocalesController < Web::ApplicationController
  def switch
    locale = params[:new_locale]
    # redirect_path = requst.referer || root_path

    unless I18n.available_locales.include?(locale&.to_sym)
      redirect_back fallback_location: root_path(locale: I18n.default_locale)
      return
    end

    if locale&.to_sym == I18n.default_locale # en == en / перключаем на en
      redirect_to root_path(locale: nil), allow_other_host: true
    else
      redirect_to root_path(locale:), allow_other_host: true
    end
  end
end
