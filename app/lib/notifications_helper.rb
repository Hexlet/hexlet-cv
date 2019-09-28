# frozen_string_literal: true

class NotificationsHelper
  include Rails.application.routes.url_helpers

  attr_reader :notification

  def initialize(notification)
    @notification = notification
  end

  delegate :kind, :resource, to: :notification

  def message
    params = send("#{kind}_params")
    I18n.t("#{kind}_html", scope: [:notifications], **params)
  end

  private

  def new_answer_params
    {
      user: resource.user,
      user_path: user_path(resource.user),
      answer_path: resume_path(resource.resume, anchor: "answer-#{resource.id}")
    }
  end

  def new_comment_params
    {
      user: resource.user,
      user_path: user_path(resource.user),
      comment_path: resume_path(resource.resume, anchor: "comment-#{resource.id}")
    }
  end

  def new_answer_comment_params
    {
      user: resource.user,
      user_path: user_path(resource.user),
      answer_comment_path: resume_path(resource.resume, anchor: "answer_comment-#{resource.id}")
    }
  end

  def new_answer_like_params
    {
      user: resource.user,
      user_path: user_path(resource.user),
      answer_path: resume_path(resource.resume, anchor: "answer-#{resource.answer.id}")
    }
  end
end
