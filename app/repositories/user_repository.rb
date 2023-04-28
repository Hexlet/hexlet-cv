# frozen_string_literal: true

module UserRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { order(id: :desc).permitted }
    scope :current_career, ->(career) { joins(:careers).where(careers: career) }
  end
end
