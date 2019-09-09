# frozen_string_literal: true

class Resume::Education < ApplicationRecord
  belongs_to :resume, dependent: :destroy

  validates :institution, presence: true
  validates :begin_date, presence: true

  def to_s
    institution
  end
end
