# frozen_string_literal: true

class Resume::Answer < ApplicationRecord
  include Resume::AnswerRepository
  # FIXME: add unique index
  validates :resume, uniqueness: { scope: :user }
  validates :content, presence: true, length: { minimum: 200 }

  belongs_to :resume, counter_cache: true
  belongs_to :user
  has_many :likes, dependent: :delete_all, inverse_of: :answer, class_name: 'Resume::Answer::Like'
  has_many :comments, dependent: :delete_all, inverse_of: :answer, class_name: 'Resume::Answer::Comment'
  has_many :notifications, as: :resource, dependent: :destroy

  def to_s
    content
  end
end
