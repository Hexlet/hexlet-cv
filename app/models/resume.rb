class Resume < ApplicationRecord
  validates :name, presence: true

  belongs_to :user
  has_many :versions, inverse_of: :resume

  accepts_nested_attributes_for :versions

  def to_s
    name
  end
end
