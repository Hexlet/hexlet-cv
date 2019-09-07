# frozen_string_literal: true

class Resume::Education < ApplicationRecord
  belongs_to :resume, dependent: :destroy

  validates :institution, presence: true
  validates :degree, presence: true
  validates :start_date, presence: true
end
