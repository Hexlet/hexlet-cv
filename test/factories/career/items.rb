# frozen_string_literal: true

# == Schema Information
#
# Table name: career_items
#
#  id             :integer          not null, primary key
#  order          :integer
#  created_at     :datetime         not null
#  updated_at     :datetime         not null
#  career_id      :integer          not null
#  career_step_id :integer          not null
#
# Indexes
#
#  index_career_items_on_career_id_and_career_step_id  (career_id,career_step_id) UNIQUE
#  index_career_items_on_career_id_and_order           (career_id,order) UNIQUE
#  index_career_items_on_career_step_id                (career_step_id)
#
# Foreign Keys
#
#  career_id       (career_id => careers.id)
#  career_step_id  (career_step_id => career_steps.id)
#
FactoryBot.define do
  factory :career_item, class: 'Career::Item' do
    order
    career_id
    career_step_id
  end
end
