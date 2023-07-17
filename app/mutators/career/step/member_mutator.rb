# frozen_string_literal: true

class Career::Step::MemberMutator
  def self.create!(career_step_member, career_member)
    user = career_member.user
    notification = nil

    next_step = career_member.next_item(career_member.current_item)&.career_step
    ActiveRecord::Base.transaction do
      career_step_member.finish!
      if career_member.may_finish?
        career_member.finish!
        notification = user.notifications.create!(kind: :career_member_finish, resource: career_member)
      elsif next_step&.notification_kind
        notification = user.notifications.create!(kind: next_step.notification_kind, resource: career_member)
      end
    end
    notification
  end
end
