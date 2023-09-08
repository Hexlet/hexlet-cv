# frozen_string_literal: true

namespace :vacancies do
  desc 'Down load vacancies from Habr'
  task download_from_habr: :environment do
    response = HabrClient.request
    if response.fail?
      Sentry.with_scope do |scope|
        scope.set_context('vacancies load error', message: 'Service is unavailable', params: response.payload.to_json)
        Sentry.capture_message('An error occurred while loading jobs')
        abort
      end
    end

    creator = User.find_by(email: ENV.fetch('EMAIL_SPECIAL_USER'))

    vacancies = response.payload['vacancies']

    vacancies.each do |vacancy|
      exist_vacnsy = Vacancy.find_by(external_id: vacancy['id'])
      next if exist_vacnsy

      salary_map = {
        rur: 'rub',
        usd: 'usd',
        eur: 'eur'
      }

      params = {
        external_id: vacancy['id'],
        creator:,
        state: :on_moderate,
        kind: :habr,
        location: vacancy['locations']&.pluck('title')&.join(', '),
        title: vacancy['title'],
        company_name: vacancy['company']['name'],
        site: vacancy['company']['url'],
        link_for_contact: vacancy['url'],
        city_name: vacancy['city'],
        conditions_description: vacancy['bonuses'],
        requirements_description: vacancy['candidate'],
        responsibilities_description: vacancy['description'],
        about_company: vacancy['team'],
        experience_description: vacancy['instructions'],
        employment_type: normalize_employment_type(vacancy['employment_type']),
        position_level: vacancy['qualification']['title']['en'].downcase,
        salary_currency: salary_map[vacancy['expanded_salary']['currency'].to_sym],
        salary_amount_type: vacancy['expanded_salary']['from'] || vacancy['expanded_salary']['to'] ? 'net' : 'depends',
        location_of_position: vacancy['remote'] ? 'remote' : 'onsite',
        salary_from: vacancy['expanded_salary']['from'],
        salary_to: vacancy['expanded_salary']['to'],
        technology_list: vacancy['skills']&.pluck('alias')&.join(', ')
      }

      Vacancy.create!(params)
    end
  end

  def normalize_employment_type(type)
    types = {
      'nepolnyy-rabochiy-den': 'part-time',
      'polnyy-rabochiy-den': 'full-time'
    }

    types[type.parameterize.to_sym] || 'full-time'
  end
end
