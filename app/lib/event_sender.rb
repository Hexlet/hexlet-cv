# frozen_string_literal: true

class EventSender
  def self.serve!(kind, resource)
    user = resource.user
    Event.create!(kind:, resource:, user:, locale: user.locale)

    EmailSender.public_send("send_#{kind}_email", resource)
  end
end
