class Resume < ApplicationRecord
  validates :link, presence: true
  belongs_to :user
  serialize :resume

  def to_s
    link
  end
end
