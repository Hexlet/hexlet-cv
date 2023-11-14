# frozen_string_literal: true

class Event < ApplicationRecord
  extend Enumerize
  include StateConcern

  validates :locale, :kind, presence: true

  belongs_to :user
  belongs_to :resource, polymorphic: true

  enumerize :kind, in: %i[new_career_member], scope: true, predicates: true

  aasm :state, column: :state do
    state :unsended, initial: true
    state :sended
    state :failed

    event :mark_as_sended do
      transitions from: %i[unsended failed], to: :sended
    end

    event :mark_as_failed do
      transitions from: :unsended, to: :failed
    end
  end
end
