# frozen_string_literal: true

class Web::VacancyFiltersController < Web::ApplicationController
  def show
    @options = fetch_options(params[:id])

    scope = Vacancy.web.page(params[:page])

    options_for_header = {
      position_level: '',
      city_name: '',
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

    keys = %i[position_level city_name direction]
    main_key = keys.find { |key| options_for_header[key].present? }
    level_key = find_level_key(@options)

    default_prefix = ".web.vacancy_filters.show.options.#{main_key}.default"

    @header = t(".options.#{main_key}.#{level_key}.header", default: :"#{default_prefix}.header", **options_for_header)
    @title = t(".options.#{main_key}.#{level_key}.title", default: :"#{default_prefix}.title", **options_for_header)
    @description = t(".options.#{main_key}.#{level_key}.description", default: :"#{default_prefix}.description", **options_for_header)

    @vacancy_search_form = Web::Vacancies::SearchForm.new(prepare_options_for_search_form(@options))
    @tags = Vacancy.directions_tags
    @vacancies = scope.distinct
    @page = params[:page]
  end

  def search
    form = Web::Vacancies::SearchForm.new(params[:web_vacancies_search_form])
    search_id = form.to_search_id
    redirect_url = search_id.blank? ? vacancies_url : vacancy_filter_url({ id: search_id })

    redirect_to redirect_url
  end

  private

  def find_level_key(options)
    options.find { |o| o.include? 'level' }&.last
  end

  def fetch_options(params)
    options = params.split('_').map { |value| value.split('-', 2) }
    if options.filter { |_k, v| v.blank? }.any?
      raise ActionController::RoutingError, 'Not Found'
    end

    options
  end

  def decode_value(value)
    Slug.decode(value)
  end

  def prepare_options_for_search_form(options)
    form_fields = options.to_h.symbolize_keys.slice(*Web::Vacancies::SearchForm::ATTRIBUTES)
    form_fields[:city] = form_fields.key?(:city) ? decode_value(form_fields[:city]) : ''

    form_fields
  end
end
