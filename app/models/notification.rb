# frozen_string_literal: true

class Notification < ApplicationRecord
  include AASM

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
