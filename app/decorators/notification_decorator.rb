# frozen_string_literal: true

class NotificationDecorator < ApplicationDecorator
  delegate_all

  def message
    params = send("#{kind}_params")
    I18n.t(kind, scope: [:notifications], **params)
  end

  def new_answer_params
    { user: resourceable.user }
  end

  def new_comment_params
    { user: resourceable.user }
  end
end
