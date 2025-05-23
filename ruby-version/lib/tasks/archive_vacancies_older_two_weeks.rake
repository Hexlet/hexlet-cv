# frozen_string_literal: true

namespace :archive_vacancies_older_two_weeks do
  desc 'This task archives vacancies that are older than 2 weeks'
  task archive_vacancies: :environment do
    old_vacancies = Vacancy.where(published_at: ..1.month.ago)

    old_vacancies.find_each do |vacancy|
      vacancy.archive! if vacancy.valid? && vacancy.published?
    end
  end
end
