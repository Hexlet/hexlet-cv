# frozen_string_literal: true

# == Schema Information
#
# Table name: events
#
#  id            :integer          not null, primary key
#  kind          :string           not null
#  locale        :string           not null
#  resource_type :string           not null
#  state         :string           not null
#  created_at    :datetime         not null
#  updated_at    :datetime         not null
#  resource_id   :integer          not null
#  user_id       :integer          not null
#
# Indexes
#
#  index_events_on_resource  (resource_type,resource_id)
#  index_events_on_user_id   (user_id)
#
# Foreign Keys
#
#  user_id  (user_id => users.id)
#
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
