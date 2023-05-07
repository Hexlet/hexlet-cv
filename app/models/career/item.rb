# frozen_string_literal: true

class Career::Item < ApplicationRecord
  include Career::ItemRepository

  validates :career_step, uniqueness: { scope: :career }
  validates :order, uniqueness: { scope: :career }

  belongs_to :career_step, class_name: 'Career::Step', inverse_of: :career_items
  belongs_to :career
end
