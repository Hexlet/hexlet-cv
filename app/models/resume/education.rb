# frozen_string_literal: true

class Resume::Education < ApplicationRecord
  include Resume::EducationRepository

  belongs_to :resume, dependent: :destroy

  validates :description, presence: true
  validates :begin_date, presence: true

  def to_s
    description
  end
end
