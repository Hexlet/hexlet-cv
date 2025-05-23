# frozen_string_literal: true

class SlugValidator < ActiveModel::EachValidator
  def validate_each(record, attribute, value)
    return if value.blank?
    return if /^[\w-]+$/i.match?(value)

    record.errors.add(attribute, :slug, **options.merge(value:))
  end
end
