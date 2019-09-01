# frozen_string_literal: true

class Resume::Version < ApplicationRecord
  belongs_to :resume
  belongs_to :user

  validates :content, presence: true
  serialize :content

  before_validation do
    self.user = resume.user
  end
end
