# frozen_string_literal: true

class Event < ApplicationRecord
  extend Enumerize
  include StateConcern

  validates :locale, :kind, presence: true

  belongs_to :user
  belongs_to :resource, polymorphic: true

  enumerize :kind, in: %i[new_career_member next_step_open_source career_member_finish
                          new_answer new_comment_to_resume new_comment_to_answer
                          answer_applied new_resume]

  aasm :state, column: :state do
    state :unsended, initial: true
    state :sended

    event :mark_as_sended do
      transitions from: :unsended, to: :sended
    end
  end
end
