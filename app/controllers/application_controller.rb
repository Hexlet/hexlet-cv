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

  def signed_in_root_path(resource_or_scope)
    scope = Devise::Mapping.find_scope!(resource_or_scope)
    router_name = Devise.mappings[scope].router_name

    home_path = "#{scope}_root_path"

    context = router_name ? send(router_name) : self
    if context.respond_to?(home_path, true)
      context.send(home_path)
    elsif context.respond_to?(:root_path)
      context.root_path(locale:)
    elsif respond_to?(:root_path)
      root_path(locale:)
    else
      "/#{locale}"
    end
  end

  private

  def redirect_root_domain
    return if request.host == ENV.fetch('HOST')

    redirect_to("#{request.protocol}#{ENV.fetch('HOST')}#{request.fullpath}", allow_other_host: true, status: :moved_permanently)
  end

  def default_url_options
    { locale: I18n.locale == I18n.default_locale ? I18n.locale : nil }
  end
end
