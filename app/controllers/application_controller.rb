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
  include DeviseHelper

  before_action :banned?
  helper_method :current_or_guest_user
  helper_method :f

  private

  def redirect_root_domain
    return if request.host == ENV.fetch('HOST')

    redirect_to("#{request.protocol}#{ENV.fetch('HOST')}#{request.fullpath}", allow_other_host: true, status: :moved_permanently)
  end

  def default_url_options
    { locale: I18n.locale == I18n.default_locale ? I18n.locale : nil }
  end
end
