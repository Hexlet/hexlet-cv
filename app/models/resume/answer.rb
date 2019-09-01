class Resume::Answer < ApplicationRecord
  belongs_to :resume
  belongs_to :user
end
