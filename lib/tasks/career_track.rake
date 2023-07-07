# frozen_string_literal: true

namespace :career_track do
  desc 'This is task find lost student in career track'
  task notify_about_lost_students: :environment do
    members = Career::Member.joins(:career_step_members)
                            .active
                            .merge(Career::Step::Member.active.where(created_at: ..1.week.ago))
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
    result = N8nClient.send_event(:lost_students, payload)

    if result.fail?
      Sentry.with_scope do |scope|
        scope.set_context('career_track_error', message: 'N8N Webhook error', params: result.payload.to_json)
        Sentry.capture_message('Error on send lost students')
      end
    end
  end
end
