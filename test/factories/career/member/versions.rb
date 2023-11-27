# frozen_string_literal: true

FactoryBot.define do
  factory :career_member_version, class: 'Career::Member::Version' do
    item_type { 'Career::Member' }
    event { 'active' }
  end
end
