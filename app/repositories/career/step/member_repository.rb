# frozen_string_literal: true

module Career::Step::MemberRepository
  extend ActiveSupport::Concern

  included do
    scope :ordered, -> { joins(career_step: :career_items).merge(Career::Item.order(order: :asc)) }
  end
end
