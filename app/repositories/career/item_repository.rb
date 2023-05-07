# frozen_string_literal: true

module Career::ItemRepository
  extend ActiveSupport::Concern

  included do
    scope :with_finished_step_members, ->(career_member) { joins(career_step: :career_step_members).merge(Career::Step::Member.finished).merge(Career::Step::Member.where(career_member:)) }
  end
end
