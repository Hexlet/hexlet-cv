# frozen_string_literal: true

class Resume::Work < ApplicationRecord
  include Resume::WorkRepository

  belongs_to :resume, dependent: :destroy

  validates :company, presence: true
  validates :position, presence: true
  validates :begin_date, presence: true
  validates :description, presence: true

  def to_s
    position
  end
end
