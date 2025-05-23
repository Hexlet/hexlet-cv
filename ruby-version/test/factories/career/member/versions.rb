# frozen_string_literal: true

# == Schema Information
#
# Table name: career_member_versions
#
#  id         :integer          not null, primary key
#  event      :string           not null
#  item_type  :string           not null
#  whodunnit  :string
#  created_at :datetime
#  item_id    :bigint           not null
#
# Indexes
#
#  index_career_member_versions_on_item_type_and_item_id  (item_type,item_id)
#
FactoryBot.define do
  factory :career_member_version, class: 'Career::Member::Version' do
    item_type { 'Career::Member' }
    event { 'active' }
  end
end
