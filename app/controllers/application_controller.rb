# frozen_string_literal: true

class ApplicationController < ActionController::Base
  include Flash
  include Pundit
  include Title
  include Sparkpost
  include Mailer
  include Auth::User

  before_action :banned?

  helper_method :title
  helper_method :meta_tag_title
  helper_method :last_notifications
  helper_method :last_answers

  before_action do
    title :base, scope: 'web'
  end

  before_action do
    gon.google_analytics_key = Rails.application.config.vars.ga
  end

  def last_notifications
    @last_notifications ||= current_user.notifications.order(created_at: :desc).limit(5) if current_user
  end

  def last_answers
    @last_answers ||= Resume::Answer.web.limit(10)
  end

  def current_or_guest_user
    current_user || guest_user
  end

  def guest_user
    Guest.new
  end
end
