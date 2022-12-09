# frozen_string_literal: true

class Web::ApplicationController < ApplicationController
  include Flash
  include GonInit
  include LocaleConcern
  # include Title
  around_action :switch_locale
  # helper_method :title
  # helper_method :meta_tag_title
  helper_method :last_notifications
  helper_method :last_answers

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
end
