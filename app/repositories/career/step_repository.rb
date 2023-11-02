# frozen_string_literal: true

module Career::StepRepository
  extend ActiveSupport::Concern
  include WithLocaleConcern

  included do
    scope :ordered, -> { joins(:career_items).merge(Career::Item.ordered) }
  end
end
