# frozen_string_literal: true

FactoryBot.define do
  factory :career_member, class: 'Career::Member' do
    user_id { 1 }
    career_id { 1 }
    finished_at { '2023-04-14 13:21:54' }
  end
end
