# frozen_string_literal: true

class Resume::Work < ApplicationRecord
  belongs_to :resume, dependent: :destroy
end
