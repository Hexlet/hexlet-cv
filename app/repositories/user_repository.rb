# frozen_string_literal: true

module UserRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { order(id: :desc).permitted }
    scope :in_career_track, -> { includes(member: :career).joins(member: :career) }
    scope :current_career, ->(career) { joins(career_members: :career).merge(Career::Member.where(state: :active, career:)) }
  end
end
