# frozen_string_literal: true

class Resume::Work < ApplicationRecord
  belongs_to :resume, dependent: :destroy

  validates :compnay, presence: true
  validates :position, presence: true
  validates :start_date, presence: true
  validates :description, presence: true
end
