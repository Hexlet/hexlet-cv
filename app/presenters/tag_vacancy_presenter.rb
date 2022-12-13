# frozen_string_literal: true

module TagVacancyPresenter
  def tags_sorted_list(sort_method = 'ASC')
    Vacancy.tags_on(:directions).order("lower(name) #{sort_method}").pluck(:name)
  end
end
