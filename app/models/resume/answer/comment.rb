# frozen_string_literal: true

# == Schema Information
#
# Table name: resume_answer_comments
#
#  id             :integer          not null, primary key
#  content        :string
#  created_at     :datetime         not null
#  updated_at     :datetime         not null
#  answer_id      :integer          not null
#  answer_user_id :integer          not null
#  resume_id      :integer          not null
#  user_id        :integer          not null
#
# Indexes
#
#  index_resume_answer_comments_on_answer_id       (answer_id)
#  index_resume_answer_comments_on_answer_user_id  (answer_user_id)
#  index_resume_answer_comments_on_resume_id       (resume_id)
#  index_resume_answer_comments_on_user_id         (user_id)
#
# Foreign Keys
#
#  answer_id       (answer_id => resume_answers.id)
#  answer_user_id  (answer_user_id => users.id)
#  resume_id       (resume_id => resumes.id)
#  user_id         (user_id => users.id)
#
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

  def author?(user)
    user_id == user.id
  end

  def self.ransackable_associations(_auth_object = nil)
    %w[answer answer_user notifications resume user]
  end

  def self.ransackable_attributes(_auth_object = nil)
    %w[answer_id answer_user_id content created_at id id_value resume_id updated_at user_id]
  end
end
