# frozen_string_literal: true

require 'acts_as_taggable_on/tag'

ActsAsTaggableOn::Tag.class_eval do
  def self.ransackable_attributes(_auth_object = nil)
    %w[created_at id name taggings_count updated_at]
  end
end
