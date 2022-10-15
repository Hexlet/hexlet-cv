# frozen_string_literal: true

class Notification < ApplicationRecord
  include AASM

  validates :kind, inclusion: { in: %w[new_answer new_nested_comment new_comment new_answer_like new_answer_comment new_answer_nested_comment answer_applied] }
  validates :resource_type, presence: true

  belongs_to :user
  belongs_to :resource, polymorphic: true

  aasm column: :state do
    state :unread, initial: true
    state :read

    event :mark_as_read do
      transitions from: :unread, to: :read
    end
  end
end
