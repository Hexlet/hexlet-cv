# frozen_string_literal: true

class Resume < ApplicationRecord
  include StateConcern
  extend Enumerize

  has_paper_trail
  is_impressionable counter_cache: true

  enumerize :english_fluency, in: %i[dont_know basic read pass_interview fluent]
  enumerize :relocation, in: %i[another_country another_city not_redy], default: :not_redy

  validates :name, presence: true
  validates :english_fluency, presence: true
  validates :github_url, presence: true
  validates :summary, presence: true, length: { minimum: 200 }
  validates :skills_description, presence: true

  belongs_to :user
  has_many :answers, inverse_of: :resume, dependent: :destroy
  has_many :answer_likes, through: :answers, source: :likes
  has_many :educations, inverse_of: :resume, dependent: :destroy
  has_many :works, inverse_of: :resume, dependent: :destroy
  has_many :comments, inverse_of: :resume, dependent: :destroy

  include ResumeRepository

  accepts_nested_attributes_for :educations, reject_if: :all_blank, allow_destroy: true
  accepts_nested_attributes_for :works, allow_destroy: true

  ransack_alias :popular, :impressions_created_at_or_comments_created_at_or_answers_created_at

  aasm :state, column: :state do
    state :draft, initial: true
    state :published
    state :archived

    event :publish do
      transitions from: %i[draft published], to: :published
    end

    event :hide do
      transitions from: %i[draft published], to: :draft
    end

    event :archive do
      transitions from: %i[published], to: :archived
    end

    event :restore do
      transitions from: %i[archived], to: :published
    end
  end

  def to_s
    name
  end
end
