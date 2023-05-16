# frozen_string_literal: true

module Career::ItemRepository
  extend ActiveSupport::Concern

  included do
    scope :with_step_members, -> { joins(career_step: :career_step_members) }
    scope :with_finished_step_members, ->(career_member) { with_step_members.merge(Career::Step::Member.finished.where(career_member:)) }
    scope :with_active_step_members, ->(career_member) { with_step_members.merge(Career::Step::Member.active.where(career_member:)) }
  end
end
