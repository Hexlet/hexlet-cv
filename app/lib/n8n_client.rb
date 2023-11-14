# frozen_string_literal: true

class N8nClient
  BASE_URI = 'https://workflows.hexlet.io'

  EVENTS = {
    lost_students: 'webhook/340-cv-career-track',
    new_career_member: 'webhook/76b9f68a-a885-47e4-9351-35dedf5e0392'
  }.freeze

  HEADER = {
    'Content-Type': 'application/json',
    Authorization: ENV.fetch('N8N_ACCESS_TOKEN')
  }.freeze

  def self.send_event(event_key, payload)
    uri = URI "#{BASE_URI}/#{EVENTS[event_key]}"

    response = Net::HTTP.post uri, payload.to_json, HEADER

    return ServiceResult.fail(payload) unless response.is_a?(Net::HTTPSuccess)

    ServiceResult.success(payload)
  end
end
