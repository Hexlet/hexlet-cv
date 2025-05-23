# frozen_string_literal: true

class Career::MemberMutator
  def self.create(career, params)
    career_member = career.members.build(params)
    user = career_member.user

    if career_member.save
      user.notifications.create!(kind: :new_career_member, resource: career_member)
    end
    career_member
  end
end
