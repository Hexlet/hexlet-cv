# frozen_string_literal: true

class Career::Member < ApplicationRecord
  include StateConcern
  include Career::MemberRepository

  validates :user, uniqueness: { scope: :career, conditions: -> { where.not(state: :archived) } }

  belongs_to :user
  belongs_to :career
  has_many :career_step_members, class_name: 'Career::Step::Member', inverse_of: :career_member, foreign_key: 'career_member_id', dependent: :destroy

  aasm :state, timestamps: true do
    state :active, initial: true
    state :finished
    state :archived

    event :mark_as_active do
      transitions from: %i[archived], to: :active
    end

    event :mark_as_finished do
      transitions from: %i[active], to: :finished
    end

    event :mark_as_archived do
      transitions from: %i[active], to: :archived
    end
  end

  def can_show_step_body?(item)
    finished_step_member_count = career_step_members.finished.size
    return item.order == 1 if finished_step_member_count.zero?

    item.order <= finished_step_member_count + 1
  end

  def career_step_members_finished?
    career_step_members.finished.size >= career.steps.size
  end
end
