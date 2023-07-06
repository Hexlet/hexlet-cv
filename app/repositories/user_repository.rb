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
  end
end
