# frozen_string_literal: true

class Career::Step::MemberMutator
  def self.find_or_create_step_member!(member, step, order)
    career_step_member = member.career_step_members.find_or_initialize_by(career_step: step, career_member: member, order:)
    career_step_member.save!
    career_step_member
  end
end
