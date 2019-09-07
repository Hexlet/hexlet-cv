# frozen_string_literal: true

class Resume::Education < ApplicationRecord
  belongs_to :resume, dependent: :destroy
end
