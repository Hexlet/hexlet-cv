# frozen_string_literal: true

# == Schema Information
#
# Table name: notifications
#
#  id            :integer          not null, primary key
#  kind          :string
#  resource_type :string           not null
#  state         :string
#  created_at    :datetime         not null
#  updated_at    :datetime         not null
#  resource_id   :integer          not null
#  user_id       :integer          not null
#
# Indexes
#
#  index_notifications_on_resource_type_and_resource_id  (resource_type,resource_id)
#  index_notifications_on_user_id                        (user_id)
#
# Foreign Keys
#
#  user_id  (user_id => users.id)
#
class Notification < ApplicationRecord
  include AASM
  extend Enumerize

  NOTIFICATION_KIND = %i[
    new_answer
    new_comment
    new_answer_like
    new_answer_comment
    answer_applied
    new_career_member
    career_member_finish
    next_step_open_source
    vacancy_publish
    vacancy_cancele
  ].freeze

  enumerize :kind, in: NOTIFICATION_KIND

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
