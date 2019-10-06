# frozen_string_literal: true

class Resume::Comment < ApplicationRecord
  include Resume::CommentRepository
  validates :content, presence: true, length: { minimum: 10, maximum: 200 }

  belongs_to :resume
  belongs_to :user
  has_many :notifications, as: :resource, dependent: :destroy

  def to_s
    content
  end
end
