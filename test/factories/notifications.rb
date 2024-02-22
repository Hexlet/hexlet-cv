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
FactoryBot.define do
  factory :notification do
    user { nil }
    resourceable { nil }
    state { 'MyString' }
    type { '' }
  end
end
