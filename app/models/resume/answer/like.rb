# frozen_string_literal: true

class Resume::Answer::Like < ApplicationRecord
  counter_culture %i[answer user], column_name: 'resume_answer_likes_count'

  validates :answer, uniqueness: { scope: %i[user] }
  validate :not_answer_owner

  belongs_to :resume
  belongs_to :answer, inverse_of: :likes, counter_cache: true
  belongs_to :user
  has_many :notifications, as: :resource, dependent: :destroy

  def not_answer_owner
    errors.add(:user, :answer_owner) if user == answer.user
  end
end
