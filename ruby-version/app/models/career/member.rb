# frozen_string_literal: true

# == Schema Information
#
# Table name: career_members
#
#  id          :integer          not null, primary key
#  finished_at :datetime
#  state       :string           not null
#  created_at  :datetime         not null
#  updated_at  :datetime         not null
#  career_id   :integer          not null
#  user_id     :integer          not null
#
# Indexes
#
#  index_career_members_on_career_id              (career_id)
#  index_career_members_on_user_id                (user_id)
#  index_career_members_on_user_id_and_career_id  (user_id,career_id) UNIQUE WHERE state = 'active'
#
# Foreign Keys
#
#  career_id  (career_id => careers.id)
#  user_id    (user_id => users.id)
#
class Career::Member < ApplicationRecord
  include StateConcern
  include Career::MemberRepository
  include Career::MemberPresenter

  validates :user, uniqueness: { conditions: -> { active } }

  belongs_to :user
  belongs_to :career
  has_many :career_step_members, class_name: 'Career::Step::Member', inverse_of: :career_member, foreign_key: 'career_member_id', dependent: :destroy
  has_many :events, as: :resource, dependent: :destroy

  has_paper_trail only: %i[state mentor_check_state], on: %i[create update], versions: {
    class_name: 'Career::Member::Version',
    scope: -> { order(created_at: :asc) }
  }

  aasm :state, timestamps: true do
    state :active, initial: true
    state :finished
    state :archived

    event :activate do
      transitions from: %i[archived], to: :active
    end

    event :finish do
      transitions from: %i[active], to: :finished, guard: :career_step_members_finished?
    end

    event :archive do
      transitions from: %i[active], to: :archived
    end

    before_all_transactions :update_version_event
  end

  def initialize(attrs = nil)
    defaults = {
      paper_trail_event: 'activate'
    }

    attrs_with_defaults = attrs ? defaults.merge(attrs) : defaults
    super(attrs_with_defaults)
  end

  def can_show_step_body?(item)
    item.order <= current_item.order
  end

  def career_step_members_finished?
    career_step_members.ordered.finished.count == career.steps.ordered.count
  end

  def current_item
    career_items = career.items.ordered
    return career_items.last if finished?

    first_active_item = career_items.with_active_step_members(self).first

    return first_active_item if first_active_item

    last_finished_item = career_items.with_finished_step_members(self).last

    return next_item(last_finished_item) || last_finished_item if last_finished_item

    career_items.first
  end

  def next_item(item)
    career.items.where(order: item.order..).ordered.second
  end

  def steps_count
    Rails.cache.fetch("member_step_count#{id}", expires_in: 12.hours) do
      career.steps.count
    end
  end

  def finished_steps_count
    Rails.cache.fetch("finished_step_count#{id}", expires_in: 1.hour) do
      career_step_members.where(career_step: career.steps).finished.count
    end
  end

  def update_version_event
    self.paper_trail_event = aasm(:state).current_event.to_s.tr('!', '')
  end

  def self.ransackable_attributes(_auth_object = nil)
    %w[created_at finished_at state updated_at user_id]
  end

  def self.ransackable_associations(_auth_object = nil)
    %w[user career versions]
  end
end
