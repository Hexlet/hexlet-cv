# frozen_string_literal: true

class Web::VacancyFiltersController < Web::ApplicationController
  def show
    @options = fetch_options(params[:id])

    scope = Vacancy.web.page(params[:page])

    options_for_header = {
      position_level: '',
      direction: '',
      city_description: ''
    }

    @options.each do |key, value|
      case key
      when 'level'
        scope = scope.where(position_level: value)
        options_for_header[:position_level] = I18n.t(value, scope: 'enumerize.position_level')
      when 'direction'
        scope = scope.tagged_with value
        options_for_header[:direction] = value
      when 'city'
        decoded_city_name = decode_value(value).downcase
        scope = scope.where(city_name: decoded_city_name)
        options_for_header[:city_description] = I18n.t('in_the_city', city_name: decoded_city_name.capitalize)
        options_for_header[:city_name] = decoded_city_name.capitalize
      end
    end

    keys = %i[position_level direction city_name]
    main_key = keys.find { |k| options_for_header.key? k }

    @header = t(".options.#{main_key}.header", **options_for_header)
    @title = t(".options.#{main_key}.title", **options_for_header)
    @description = t(".options.#{main_key}.description", **options_for_header)

    @vacancy_search_form = Web::Vacancies::SearchForm.new(prepare_options_for_search_form(@options))
    @vacancies = scope
  end

  def search
    search_id = convert_search_query_to_string(search_params)
    redirect_url = search_id.blank? ? vacancies_url : vacancy_filter_url({ id: search_id })

    redirect_to redirect_url
  end

  private

  def fetch_options(params)
    options = params.split('_').map { |value| value.split('-', 2) }
    if options.filter { |_k, v| v.blank? }.any?
      raise ActionController::RoutingError, 'Not Found'
    end

    options
  end

  def search_params
    params.require(:web_vacancies_search_form).permit(:level, :city, :direction)
  end

  def decode_value(value)
    Slug.decode(value)
  end

  def convert_search_query_to_string(query)
    q = query.to_h.filter { |_k, v| v.present? }.transform_values(&:strip).symbolize_keys
    return '' if q.empty?

    helpers.filter_slug(q)
  end

  def prepare_options_for_search_form(options)
    search_fields = %w[level direction city]
    result = options.to_h.filter { |_k, v| search_fields.include?(v) }.symbolize_keys
    if result.key?(:city)
      city = result[:city]
      result[:city] = decode_value(city).capitalize
    end

    result
  end
end
