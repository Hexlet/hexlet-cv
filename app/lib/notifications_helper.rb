# frozen_string_literal: true

class NotificationsHelper
  attr_reader :notification

  def initialize(notification)
    @notification = notification
  end

  delegate :kind, :resource, to: :notification

  def message
    params = send("#{kind}_params")
    I18n.t(kind, scope: [:notifications], **params)
  end

  private

  def new_answer_params
    { user: resource.user }
  end

  def new_comment_params
    { user: resource.user }
  end

  def new_answer_comment_params
    { user: resource.user }
  end

  def new_answer_like_params
    { user: resource.user }
  end
end
