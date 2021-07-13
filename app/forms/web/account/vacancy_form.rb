# frozen_string_literal: true

class Web::Account::VacancyForm < Vacancy
  include ActiveFormModel

  permit :title
end
