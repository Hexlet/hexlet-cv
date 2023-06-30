# frozen_string_literal: true

module UserRepository
  extend ActiveSupport::Concern

  included do
    scope :ordered, -> { order(id: :desc) }
    scope :web, -> { ordered.permitted }
    scope :with_careers, -> { joins(:careers).distinct }
  end
end
