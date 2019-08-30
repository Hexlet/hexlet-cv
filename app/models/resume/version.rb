class Resume::Version < ApplicationRecord
  belongs_to :resume
  belongs_to :user

  validates :content, presence: true
  serialize :content
end
