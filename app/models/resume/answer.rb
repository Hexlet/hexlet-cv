# frozen_string_literal: true

# == Schema Information
#
# Table name: resume_answers
#
#  id               :integer          not null, primary key
#  applying_state   :string
#  content          :text
#  likes_count      :integer
#  publishing_state :string           default("published")
#  created_at       :datetime         not null
#  updated_at       :datetime         not null
#  resume_id        :integer          not null
#  user_id          :integer          not null
#
# Indexes
#
#  index_resume_answers_on_resume_id              (resume_id)
#  index_resume_answers_on_user_id                (user_id)
#  index_resume_answers_on_user_id_and_resume_id  (user_id,resume_id) UNIQUE
#
# Foreign Keys
#
#  resume_id  (resume_id => resumes.id)
#  user_id    (user_id => users.id)
#
class Resume::Answer < ApplicationRecord
  include AASM
  include Resume::AnswerRepository
  # FIXME: add unique index
  validates :resume, uniqueness: { scope: :user }
  validates :content, presence: true, length: { minimum: 10 }

  belongs_to :resume, counter_cache: true
  belongs_to :user
  has_many :likes, dependent: :destroy, inverse_of: :answer, class_name: 'Resume::Answer::Like'
  has_many :comments, dependent: :destroy, inverse_of: :answer, class_name: 'Resume::Answer::Comment'
  has_many :notifications, as: :resource, dependent: :destroy

  aasm :applying, column: :applying_state do
    state :pending, initial: true
    state :applied

    event :apply do
      transitions from: %i[applied pending], to: :applied
    end
  end

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

  def tota_ai_author?
    user.email == ENV.fetch('EMAIL_SPECIAL_USER')
  end

  def self.ransackable_attributes(_auth_object = nil)
    %w[id content user_id publishing_state created_at]
  end

  def self.ransackable_associations(_auth_object = nil)
    %w[comments likes notifications resume user]
  end
end
