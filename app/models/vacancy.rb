# frozen_string_literal: true

class Vacancy < ApplicationRecord
  include StateConcern
  extend Enumerize

  validates :title, presence: true
  validates :company_name, presence: true
  validates :description, presence: true
  validates :site, presence: true
  validates :language, presence: true

  belongs_to :creator, class_name: 'User'
  belongs_to :country, optional: true

  aasm :state, column: :state do
    state :idle
    state :on_moderate
    state :published
    state :archived

    event :publish do
      transitions from: :all, to: :published
    end
  end

  def to_s
    title
  end
end
