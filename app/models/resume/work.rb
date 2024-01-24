# frozen_string_literal: true

class Resume::Work < ApplicationRecord
  include Resume::WorkRepository

  belongs_to :resume

  validates :company, presence: true
  validates :company_description, length: { maximum: 200 }
  validates :position, presence: true
  validates :begin_date, presence: true
  validates :description, presence: true

  def to_s
    position
  end
end
