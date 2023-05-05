# frozen_string_literal: true

FactoryBot.define do
  factory :career_step_member, class: 'Career::Step::Member' do
    step_state { 'active' }
    career_member_id
    career_step_id
    order
  end
end