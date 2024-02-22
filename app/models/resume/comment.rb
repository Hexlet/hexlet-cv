# frozen_string_literal: true

# == Schema Information
#
# Table name: resume_comments
#
#  id         :integer          not null, primary key
#  content    :string
#  created_at :datetime         not null
#  updated_at :datetime         not null
#  resume_id  :integer
#  user_id    :integer
#
# Indexes
#
#  index_resume_comments_on_resume_id  (resume_id)
#  index_resume_comments_on_user_id    (user_id)
#
class Resume::Comment < ApplicationRecord
  include Resume::CommentRepository
  validates :content, presence: true, length: { minimum: 10, maximum: 400 }

  belongs_to :resume
  belongs_to :user
  has_many :notifications, as: :resource, dependent: :destroy

  def to_s
    content
  end

  def author?(user)
    user_id == user.id
  end
end
