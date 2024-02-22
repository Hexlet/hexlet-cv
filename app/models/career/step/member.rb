# frozen_string_literal: true

# == Schema Information
#
# Table name: career_step_members
#
#  id               :integer          not null, primary key
#  state            :string           not null
#  created_at       :datetime         not null
#  updated_at       :datetime         not null
#  career_member_id :integer          not null
#  career_step_id   :integer          not null
#
# Indexes
#
#  index_career_step_members_on_career_step_id         (career_step_id)
#  index_career_step_members_on_member_id_and_step_id  (career_member_id,career_step_id) UNIQUE
#
# Foreign Keys
#
#  career_member_id  (career_member_id => career_members.id)
#  career_step_id    (career_step_id => career_steps.id)
#
class Career::Step::Member < ApplicationRecord
  include StateConcern
  include Career::Step::MemberRepository

  validates :career_step, uniqueness: { scope: :career_member }

  belongs_to :career_step, class_name: 'Career::Step', inverse_of: :career_step_members
  belongs_to :career_member, class_name: 'Career::Member', inverse_of: :career_step_members
  has_one :user, through: :career_member

  aasm :state do
    state :active, initial: true
    state :finished

    event :finish do
      transitions from: :active, to: :finished
    end
  end
end
