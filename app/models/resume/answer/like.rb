# frozen_string_literal: true

class Resume::Answer::Like < ApplicationRecord
  validates :resume, uniqueness: { scope: %i[answer user] }

  # TODO: add unique index
  belongs_to :resume
  belongs_to :answer, foreign_key: 'resume_answer_id', inverse_of: :likes, counter_cache: true
  belongs_to :user
end
