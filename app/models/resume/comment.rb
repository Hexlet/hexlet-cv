# frozen_string_literal: true

class Resume::Comment < ApplicationRecord
  validates :content, presence: true, length: { minimum: 10, maximum: 200 }

  belongs_to :resume
  belongs_to :user

  def to_s
    content
  end
end
