# frozen_string_literal: true

class ArchiveVacanciesOlderTwoWeeks < ActiveRecord::Migration[7.0]
  def change
    Vacancy.find_each do |vacancy|
      vacancy.archive! if vacancy.published? && vacancy.published_at < 2.weeks.ago
    end
  end
end
