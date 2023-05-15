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
      transitions from: %i[active], to: :finished, guard: :career_step_members_finished?
    end

    event :mark_as_archived do
      transitions from: %i[active], to: :archived
    end
  end

  def can_show_step_body?(item)
    item.order <= current_item.order
  end

  def career_step_members_finished?
    career_step_members.finished.last&.career_step == career.steps.last
  end

  def current_item
    first_active_item = career.items.order(order: :asc).with_active_step_members(self).first
    last_finished_item = career.items.order(order: :asc).with_finished_step_members(self).last

    return first_active_item if first_active_item.present?

    last_finished_item
  end

  def next_item
    item = current_item
    next_item = career.items.order(order: :asc).where(order: item.order..).second

    return item if next_item.blank?

    next_item
  end
end
