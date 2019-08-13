class Resume < ApplicationRecord
  validates :link, presence: true
  belongs_to :user

  def to_s
    link
  end
end
