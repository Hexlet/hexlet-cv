# frozen_string_literal: true

module Career::StepRepository
  extend ActiveSupport::Concern

  included do
    scope :ordered, -> { includes(:career_items).merge(Career::Item.ordered) }
  end
end
