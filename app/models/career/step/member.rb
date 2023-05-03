# frozen_string_literal: true

class Career::Step::Member < ApplicationRecord
  include StateConcern

  validates :career_step, uniqueness: { scope: :career_member }
  validates :order, presence: true

  belongs_to :career_step, class_name: 'Career::Step', inverse_of: :career_step_members
  belongs_to :career_member, class_name: 'Career::Member', inverse_of: :career_step_members
  has_one :user, through: :career_member

  aasm :step_state, column: :step_state do
    state :working, initial: true
    state :finished

    event :finish do
      transitions from: :working, to: :finished
    end
  end
end
