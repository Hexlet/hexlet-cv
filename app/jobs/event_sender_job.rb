# frozen_string_literal: true

class EventSenderJob < ApplicationJob
  queue_as :default

  retry_on StandardError, wait: 5.seconds, attempts: 3

  def perform(event_id)
    event = Event.find(event_id)
    public_send("process_#{event.kind}_created", event)
  end

  def process_new_career_member_created(event)
    user = event.user
    career = event.resource.career

    payload = {
      full_name: user.full_name,
      email: user.email,
      career_name: career.name,
      career_url: Rails.application.routes.url_helpers.career_member_url(career.slug, event.resource, locale: I18n.locale, host: ENV.fetch('HOST')),
      event_kind: event.kind
    }

    result = N8nClient.send_event(event.kind, payload)

    if result.fail?
      event.mark_as_failed! if event.may_mark_as_failed?
      raise
    end

    event.mark_as_sended!
  end
end
