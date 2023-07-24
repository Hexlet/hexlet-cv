# frozen_string_literal: true

class EventSender
  def self.serve!(kind, resource, options = {})
    user = resource.user
    Event.create!(kind:, resource:, user:, locale: user.locale)

    # NOTE: здесь будет job-a которая будет отправлять событие в n8n
    EmailSender.public_send("send_#{kind}_email", resource) if options[:after_transaction]
  end
end
