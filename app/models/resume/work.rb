# frozen_string_literal: true

class Resume::Work < ApplicationRecord
  belongs_to :resume, dependent: :destroy

  validates :company, presence: true
  validates :position, presence: true
  validates :begin_date_month, presence: true, inclusion: { in: 1..12 }
  validates :begin_date_year, presence: true
  validates :end_date_month, inclusion: { in: 1..12 }
  validates :description, presence: true

  def to_s
    position
  end

  def begin_date_full
    "#{self.begin_date_month}-#{self.begin_date_year}"
  end

  def end_date_full
    "#{self.end_date_month}-#{self.end_date_year}"
  end
end
