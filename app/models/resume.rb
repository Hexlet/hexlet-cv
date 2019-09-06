# frozen_string_literal: true

class Resume < ApplicationRecord
  extend Enumerize
  has_paper_trail

  enumerize :english_fluency, in: %i[dont_know basic read pass_interview fluent]

  validates :name, presence: true
  validates :english_fluency, presence: true
  validates :github_url, presence: true
  validates :summary, presence: true, length: { minimum: 200, maximum: 500 }
  validates :skills_description, presence: true
  validates :awards_description, presence: true

  belongs_to :user
  has_many :answers, dependent: :destroy
  has_many :educations, inverse_of: :resume
  has_many :works, inverse_of: :resume

  accepts_nested_attributes_for :educations, reject_if: :all_blank, allow_destroy: true
  accepts_nested_attributes_for :works, allow_destroy: true

  def to_s
    name
  end
end
