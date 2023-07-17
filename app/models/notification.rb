# frozen_string_literal: true

class Notification < ApplicationRecord
  include AASM

  validates :kind, inclusion: {
    in: %w[new_answer new_comment new_answer_like new_answer_comment answer_applied new_career_member career_member_finish next_step_open_source]
  }
  validates :resource_type, presence: true

  belongs_to :user
  belongs_to :resource, polymorphic: true

  aasm :state do
    state :unread, initial: true
    state :read

    event :mark_as_read do
      transitions from: :unread, to: :read
    end
  end

  def self.ransackable_attributes(_auth_object = nil)
    %w[state created_at]
  end
end
