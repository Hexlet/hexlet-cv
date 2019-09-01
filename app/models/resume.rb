# frozen_string_literal: true

class Resume < ApplicationRecord
  has_paper_trail only: [:content]

  serialize :content

  validates :name, presence: true
  validates :content, presence: true

  belongs_to :user

  def to_s
    name
  end

  def summary
    content.dig(:basics, :summary)
  end
end
