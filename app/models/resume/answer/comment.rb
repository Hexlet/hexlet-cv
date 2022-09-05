# frozen_string_literal: true

class Resume::Answer::Comment < ApplicationRecord
  validates :content, presence: true, length: { minimum: 10, maximum: 400 }

  belongs_to :resume
  belongs_to :answer, inverse_of: :comments
  belongs_to :user
  belongs_to :answer_user, class_name: 'User'
  has_many :notifications, as: :resource, dependent: :destroy

  include Resume::Answer::CommentRepository

  def to_s
    content
  end
end
