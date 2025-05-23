# frozen_string_literal: true

# == Schema Information
#
# Table name: career_step_members
#
#  id               :integer          not null, primary key
#  state            :string           not null
#  created_at       :datetime         not null
#  updated_at       :datetime         not null
#  career_member_id :integer          not null
#  career_step_id   :integer          not null
#
# Indexes
#
#  index_career_step_members_on_career_step_id         (career_step_id)
#  index_career_step_members_on_member_id_and_step_id  (career_member_id,career_step_id) UNIQUE
#
# Foreign Keys
#
#  career_member_id  (career_member_id => career_members.id)
#  career_step_id    (career_step_id => career_steps.id)
#
FactoryBot.define do
  factory :career_step_member, class: 'Career::Step::Member' do
    state { 'active' }
    career_member_id
    career_step_id
  end
end
