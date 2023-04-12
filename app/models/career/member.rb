# frozen_string_literal: true

class Career::Member < ApplicationRecord
  include StateConcern

  validates :user, uniqueness: { scope: :career, conditions: -> { active } }

  belongs_to :user
  belongs_to :career

  aasm :state do
    state :active, initial: true
    state :archived

    event :mark_as_active do
      transitions from: %i[archived], to: :active
    end

    event :mark_as_archived do
      transitions from: %i[active], to: :archived
    end
  end
end
