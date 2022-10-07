# frozen_string_literal: true

class Resume::Answer < ApplicationRecord
  include AASM
  include Resume::AnswerRepository
  # FIXME: add unique index
  validates :resume, uniqueness: { scope: :user }
  validates :content, presence: true, length: { minimum: 20 }

  belongs_to :resume, counter_cache: true
  belongs_to :user
  has_many :likes, dependent: :delete_all, inverse_of: :answer, class_name: 'Resume::Answer::Like'
  has_many :comments, dependent: :delete_all, inverse_of: :answer, class_name: 'Resume::Answer::Comment'
  has_many :notifications, as: :resource, dependent: :destroy

  aasm :applying, column: :applying_state do
    state :pending, initial: true
    state :applied

    event :apply do
      transitions from: %i[applied pending], to: :applied
    end
  end

  def to_s
    content
  end
end
