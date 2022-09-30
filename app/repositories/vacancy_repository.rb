# frozen_string_literal: true

module VacancyRepository
  extend ActiveSupport::Concern

  included do
    scope :web, -> { published.order(id: :desc) }
    scope :tags_sorted_list,
          -> { tags_on(:directions).order('lower(name) ASC').pluck(:name) }
  end
end
