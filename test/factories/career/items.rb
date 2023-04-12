# frozen_string_literal: true

FactoryBot.define do
  factory :career_item, class: 'Career::Item' do
    order
    career_id
    step_id
  end
end
