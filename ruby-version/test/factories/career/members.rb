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
FactoryBot.define do
  factory :career_member, class: 'Career::Member' do
    user_id { 1 }
    career_id { 1 }
  end
end
