# frozen_string_literal: true

require 'application_responder'

# frozen_string_literal: true

class ApplicationController < ActionController::Base
  before_action :redirect_root_domain

  self.responder = ApplicationResponder
  respond_to :html, :json

  include Pundit::Authorization
  include Sparkpost
  include Auth
  include Flash  
  before_action :banned?
  helper_method :current_or_guest_user
  helper_method :f
  def current_or_guest_user
    current_user || guest_user
  end

  def guest_user
    Guest.new
  end

  private

  def redirect_root_domain
    return if request.host == ENV.fetch('HOST')

    redirect_to("#{request.protocol}#{ENV.fetch('HOST')}#{request.fullpath}", status: :moved_permanently)
  end

  def default_url_options(_options = {})
    { locale: I18n.locale == I18n.default_locale ? nil : I18n.locale }
  end  
end
