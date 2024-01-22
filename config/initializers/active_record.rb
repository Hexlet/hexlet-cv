# frozen_string_literal: true

module OutdatedAttributes
  extend ActiveSupport::Concern

  class_methods do
    def mark_as_outdated(*attributes)
      @outdated_attributes = Set.new(attributes.map(&:to_s))
      rewrite_outdated_getters
      rewrite_outdated_setters
    end

    def outdated_attributes
      @outdated_attributes || Set.new
    end

    def rewrite_outdated_getters
      outdated_attributes.each do |attribute|
        define_method attribute.to_s do
          raise "Attribute #{attribute} for class '#{self.class}' was marked as outdated"
        end
      end
    end

    def rewrite_outdated_setters
      outdated_attributes.each do |attribute|
        define_method :"#{attribute}=" do |_value|
          raise "Attribute #{attribute} for class '#{self.class}' was marked as outdated"
        end
      end
    end
  end
end

class ActiveRecord::Base
  include OutdatedAttributes
end
