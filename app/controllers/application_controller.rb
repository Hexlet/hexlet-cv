# frozen_string_literal: true

class ApplicationController < ActionController::Base
  include Flash
  include Pundit
  include Title

  helper_method :title
  helper_method :meta_tag_title
  helper_method :last_notifications
  helper_method :last_answers

  before_action do
    title :base, scope: 'web'
  end

  def last_notifications
    @last_notifications ||= current_user.notifications.order(created_at: :desc).limit(5) if current_user
  end

  def last_answers
    @last_answers = Resume::Answer.limit(10).order(id: :desc)
  end
end
