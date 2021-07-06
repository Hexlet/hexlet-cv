# frozen_string_literal: true

class ApplicationController < ActionController::Base
  include Pundit
  include Sparkpost
  include Mailer
  include Auth::User

  before_action :banned?

  def current_or_guest_user
    current_user || guest_user
  end

  def guest_user
    Guest.new
  end
end
