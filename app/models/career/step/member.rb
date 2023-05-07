# frozen_string_literal: true

class Career::Step::Member < ApplicationRecord
  include StateConcern
  include Career::Step::MemberRepository

  validates :career_step, uniqueness: { scope: :career_member }

  belongs_to :career_step, class_name: 'Career::Step', inverse_of: :career_step_members
  belongs_to :career_member, class_name: 'Career::Member', inverse_of: :career_step_members
  has_one :user, through: :career_member

  aasm :step_state, column: :step_state do
    state :active, initial: true
    state :finished

    event :finish do
      transitions from: :active, to: :finished
    end
  end
end
