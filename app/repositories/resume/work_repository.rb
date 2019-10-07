# frozen_string_literal: true

module Resume::WorkRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { order(end_date: :desc) }
  end
end
