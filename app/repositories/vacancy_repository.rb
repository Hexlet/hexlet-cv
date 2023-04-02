# frozen_string_literal: true

module VacancyRepository
  extend ActiveSupport::Concern
  include WithLocaleConcern

  included do
    scope :web, -> { with_locale.published.order(published_at: :desc) }
  end
end
