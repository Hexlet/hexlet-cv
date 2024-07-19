# frozen_string_literal: true

class NotificationsHelper
  class << self
    include Rails.application.routes.url_helpers

    def notification_class(notification)
      notification_class_map = {
        new_answer: 'fa-file-alt',
        new_comment: 'fa-comment',
        new_answer_comment: 'fa-comment',
        new_answer_like: 'fa-thumbs-up',
        answer_applied: 'fa-heart text-success'
      }

      notification_class_map[notification.kind.to_sym]
    end

    def message(notification)
      raise [notification, notification.user].inspect unless notification.resource

      params = send(:"#{notification.kind}_params", notification.resource)
      I18n.t("#{notification.kind}_html", scope: [:notifications], **params)
    end

    def new_answer_params(resource)
      {
        user: resource.user,
        user_path: user_path(resource.user, locale: I18n.locale),
        answer_path: resume_path(resource.resume, anchor: "answer-#{resource.id}", locale: I18n.locale)
      }
    end

    def new_comment_params(resource)
      {
        user: resource.user,
        user_path: user_path(resource.user, locale: I18n.locale),
        comment_path: resume_path(resource.resume, anchor: "comment-#{resource.id}", locale: I18n.locale)
      }
    end

    def new_answer_comment_params(resource)
      {
        user: resource.user,
        user_path: user_path(resource.user, locale: I18n.locale),
        answer_comment_path: resume_path(resource.resume, locale: I18n.locale, anchor: "answer_comment-#{resource.id}")
      }
    end

    def answer_applied_params(answer)
      {
        user: answer.resume.user,
        user_path: user_path(answer.resume.user, locale: I18n.locale),
        answer_path: resume_path(answer.resume, locale: I18n.locale, anchor: "answer-#{answer.id}")
      }
    end

    def new_answer_like_params(resource)
      {
        user: resource.user,
        user_path: user_path(resource.user, locale: I18n.locale),
        answer_path: resume_path(resource.resume, locale: I18n.locale, anchor: "answer-#{resource.answer.id}")
      }
    end

    def new_career_member_params(resource)
      career = resource.career
      {
        career: career.name,
        career_path: career_member_path(career.slug, resource, locale: I18n.locale)
      }
    end

    def career_member_finish_params(resource)
      career = resource.career
      {
        career: career.name,
        career_path: career_member_path(career.slug, resource, locale: I18n.locale)
      }
    end

    def next_step_open_source_params(resource)
      career = resource.career
      {
        career_path: career_member_path(career.slug, resource, locale: I18n.locale)
      }
    end

    def vacancy_publish_params(resource)
      {
        vacancy_path: vacancy_path(resource, locale: I18n.locale)
      }
    end

    def vacancy_cancele_params(resource)
      {
        vacancy_path: vacancy_path(resource, locale: I18n.locale),
        cancelation_reason: resource.cancelation_reason_text
      }
    end
  end
end
