# frozen_string_literal: true

module VacancyRepository
  extend ActiveSupport::Concern
  include WithLocaleConcern

  included do
    scope :web, -> { with_locale.published }
  end
end
