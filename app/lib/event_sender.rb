# frozen_string_literal: true

class EventSender
  def self.serve!(kind, resource)
    user = resource.user
    event = Event.create!(kind:, resource:, user:, locale: user.locale)

    EventSenderJob.perform_later(event.id) if user.can_send_email?
  end
end
