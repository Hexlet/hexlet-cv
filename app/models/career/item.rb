# frozen_string_literal: true

class Career::Item < ApplicationRecord
  validates :step_id, uniqueness: { scope: :career_id }

  belongs_to :step
  belongs_to :career
end
