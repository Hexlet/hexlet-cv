# frozen_string_literal: true

module UserRepository
  extend ActiveSupport::Concern

  included do
    scope :ordered, -> { order(id: :desc) }
    scope :web, -> { ordered.permitted }
    scope :with_career_members, lambda {
                                  joins(:career_members)
                                    .where('EXISTS (:career_member)',
                                           career_member: Career::Member.select('1').where('"users".id = career_members.user_id')).limit(1)
                                }
    scope :only_active_career_members, -> { with_career_members.merge(Career::Member.active) }
    scope :only_archived_career_members, -> { with_career_members.merge(Career::Member.archived) }
    scope :only_finished_career_members, -> { with_career_members.merge(Career::Member.finished) }
    scope :with_career_step_members, -> { joins(career_members: :career_step_members) }
  end
end
