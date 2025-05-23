# frozen_string_literal: true

module ActsAsTaggableOn
  class Tag < ::ActiveRecord::Base # rubocop:disable Rails/ApplicationRecord
    def self.ransackable_attributes(_auth_object = nil)
      %w[id name taggings_count created_at updated_at]
    end
  end
end
