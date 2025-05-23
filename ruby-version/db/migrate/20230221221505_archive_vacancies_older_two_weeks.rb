# frozen_string_literal: true

class ArchiveVacanciesOlderTwoWeeks < ActiveRecord::Migration[7.0]
  def change
    old_vacancies = Vacancy.where(published_at: ..2.weeks.ago)
    old_vacancies.find_each do |vacancy|
      vacancy.archive! if can_archive?(vacancy)
    end
  end

  private

  def can_archive?(vacancy)
    vacancy.valid? && vacancy.published?
  end
end
