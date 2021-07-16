# frozen_string_literal: true

class Web::VacancyFiltersController < Web::ApplicationController
  def show
    @options = params[:id].split('_').map { |value| value.split('-') }

    scope = Vacancy.web.page(params[:page])
    @options.each do |key, value|
      case key
      when 'level'
        scope = scope.where(position_level: value)
      when 'technology'
        scope = scope.tagged_with value
      when 'city'
        scope = scope.where(city_name: Slug.decode(value).downcase)
      end
    end

    @vacancies = scope
  end
end
