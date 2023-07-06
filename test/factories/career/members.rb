# frozen_string_literal: true

FactoryBot.define do
  factory :career_member, class: 'Career::Member' do
    user_id { 1 }
    career_id { 1 }
  end
end
