# frozen_string_literal: true

# == Schema Information
#
# Table name: resume_comments
#
#  id               :integer          not null, primary key
#  content          :string
#  publishing_state :string           default("published")
#  created_at       :datetime         not null
#  updated_at       :datetime         not null
#  resume_id        :integer
#  user_id          :integer
#
# Indexes
#
#  index_resume_comments_on_resume_id  (resume_id)
#  index_resume_comments_on_user_id    (user_id)
#
class Resume::Comment < ApplicationRecord
  include AASM
  include Resume::CommentRepository
  validates :content, presence: true, length: { minimum: 10, maximum: 400 }

  belongs_to :resume
  belongs_to :user
  has_many :notifications, as: :resource, dependent: :destroy

  aasm :publishing, column: :publishing_state do
    state :published, initial: true
    state :archived

    event :archive do
      transitions from: :published, to: :archived
    end

    event :restore do
      transitions from: :archived, to: :published
    end
  end

  def to_s
    content
  end

  def author?(user)
    user_id == user.id
  end

  def self.ransackable_attributes(_auth_object = nil)
    %w[content created_at id id_value resume_id updated_at user_id]
  end

  def self.ransackable_associations(_auth_object = nil)
    %w[notifications resume user]
  end
end
