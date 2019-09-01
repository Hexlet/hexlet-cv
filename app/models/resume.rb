# frozen_string_literal: true

class Resume < ApplicationRecord
  has_paper_trail

  validates :name, presence: true
  validates :github_url, presence: true
  validates :summary, presence: true, length: { minimum: 200, maximum: 500 }
  validates :skills_description, presence: true
  validates :awards_description, presence: true

  belongs_to :user
  has_many :answers, dependent: :destroy

  def to_s
    name
  end
end
