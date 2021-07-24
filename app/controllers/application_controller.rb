require "application_responder"

# frozen_string_literal: true

class ApplicationController < ActionController::Base
  self.responder = ApplicationResponder
  respond_to :html, :json

  include Pundit
  include Sparkpost
  include Mailer
  include Auth

  before_action :banned?
  helper_method :current_or_guest_user

  def current_or_guest_user
    current_user || guest_user
  end

  def guest_user
    Guest.new
  end
end
