# frozen_string_literal: true

class EventSenderJob < ApplicationJob
  queue_as :default

  retry_on StandardError, wait: 5.seconds, attempts: 3

  def perform(event_id)
    event = Event.find(event_id)
    public_send(:"process_#{event.kind}_created", event)
  end
end
