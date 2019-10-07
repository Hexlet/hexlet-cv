# frozen_string_literal: true

class Resume::Answer::Like < ApplicationRecord
  validates :resume, uniqueness: { scope: %i[answer user] }
  validate :not_answer_owner

  # TODO: add unique index
  belongs_to :resume
  belongs_to :answer, inverse_of: :likes, counter_cache: true
  belongs_to :user
  has_many :notifications, as: :resource, dependent: :destroy

  def not_answer_owner
    errors.add(:user, :answer_owner) if user == answer.user
  end
end
