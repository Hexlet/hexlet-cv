# frozen_string_literal: true

class Web::Vacancies::SearchForm
  include ActiveFormModel::Virtual
  include ApplicationHelper

  ATTRIBUTES = %i[level city direction].freeze

  fields(*ATTRIBUTES)

  def to_search_id
    search_id = search_params.compact_blank.transform_values(&:strip).symbolize_keys
    return '' if search_id.empty?

    filter_slug(search_id)
  end

  def city
    return @city.capitalize if @city.respond_to?(:capitalize)

    @city
  end

  private

  def search_params
    params = ATTRIBUTES.map do |attr_name|
      value = instance_variable_get("@#{attr_name}")

      [attr_name, value]
    end

    params.to_h
  end
end
