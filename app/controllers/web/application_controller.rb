# frozen_string_literal: true

class Web::ApplicationController < ApplicationController
  include ActionView::Helpers::UrlHelper
  include Flash
  include GonInit
  include LocaleConcern
  # include Title

  # helper_method :title
  # helper_method :meta_tag_title
  helper_method :last_notifications
  helper_method :last_answers

  before_action :prepare_locale_settings

  # before_action do
  #   title :base, scope: 'web'
  # end

  # def last_notifications
  #   @last_notifications ||= current_user.notifications.order(created_at: :desc).limit(5) if current_user
  # end

  def last_answers
    @last_answers ||= Resume::Answer.web.order(id: :desc).limit(10)
  end

  # NOTE: https://github.com/charlotte-ruby/impressionist/pull/294
  def session_hash
    id = session.id || request.session_options[:id]

    if id.respond_to?(:cookie_value)
      id.cookie_value
    elsif id.is_a?(Rack::Session::SessionId)
      id.public_id
    end
  end

  private

  def prepare_locale_settings
    if browser.bot?
      I18n.locale = params[:locale] || I18n.default_locale
      return
    end

    if current_page?(root_path) && !params[:locale]
      remembered_locale = session[:locale].presence
      if remembered_locale
        if remembered_locale.to_sym != I18n.default_locale
          redirect_to root_url(locale: remembered_locale), allow_other_host: true
        end
      else
        ru_country_codes = ['RU']
        if locale_from_accept_language_header == :ru || ru_country_codes.include?(country_by_ip)
          redirect_to root_url(locale: :ru), allow_other_host: true
        end
      end
    else
      session[:locale] = I18n.locale
    end
  end

  def country_by_ip
    @country_by_ip = Geocoder.search(request.remote_ip).first&.country_code || 'EN'
  end

  def locale_from_accept_language_header
    request.env['HTTP_ACCEPT_LANGUAGE']&.scan(/^[a-z]{2}/)&.first&.downcase&.to_sym
  end
end
