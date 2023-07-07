# frozen_string_literal: true

namespace :n8n_sender do
  desc 'This is task find lost student in career track'
  task find_lost_student: :environment do
    header = {
      'Content-Type': 'application/json',
      Authorization: ENV.fetch('N8N_ACCESS_TOKEN')
    }
    uri = URI 'https://workflows.hexlet.io/webhook-test/340-cv-career-track'
    members = Career::Member.joins(:career_step_members).merge(Career::Step::Member.active.where(created_at: ..1.week.ago))
    abort 'No lost student' if members.empty?

    payload = members.each_with_object({}) do |member, acc|
      acc[member.id] = {
        full_name: member.user.full_name,
        email: member.user.email,
        career_track: member.career.name,
        finished_steps_count: member.finished_steps_count,
        last_activity_at: member.career_step_members.active.last.created_at
      }
    end
    response = Net::HTTP.post uri, payload.to_json, header

    unless response.is_a?(Net::HTTPSuccess)
      Sentry.with_scope do |scope|
        scope.set_context('n8n_sender_error', message: 'N8N Webhook error', params: payload.to_json)
        Sentry.capture_message('Error on send lost students')
      end
    end
  end
end
