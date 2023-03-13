# frozen_string_literal: true

require 'test_helper'
require 'rake'

class ArchiveVacanciesTest < ActiveSupport::TestCase
  def setup
    HexletCv::Application.load_tasks if Rake::Task.tasks.empty?
  end

  test 'archive vacancies' do
    vacancy = vacancies(:over_two_weeks_old)

    assert { vacancy.published? }

    Rake::Task['archive_vacancies_older_two_weeks:archive_vacancies'].invoke

    vacancy.reload
    assert { vacancy.archived? }
  end
end
