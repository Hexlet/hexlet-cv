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
class Career::Item < ApplicationRecord
  include Career::ItemRepository

  validates :career_step, uniqueness: { scope: :career }
  validates :order, uniqueness: { scope: :career }

  belongs_to :career_step, class_name: 'Career::Step', inverse_of: :career_items
  belongs_to :career
end
