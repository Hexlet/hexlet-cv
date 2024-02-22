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
FactoryBot.define do
  factory :event do
    user { nil }
    locale { 'ru' }
  end
end
