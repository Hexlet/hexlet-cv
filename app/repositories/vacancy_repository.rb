# frozen_string_literal: true

module VacancyRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { published.order(published_at: :desc) }
  end
end
