# frozen_string_literal: true

module TagVacancyPresenter
  def directions_tags(sort_method = 'ASC')
    Vacancy.tags_on(:directions).order("lower(name) #{sort_method}").pluck(:name)
  end
end
