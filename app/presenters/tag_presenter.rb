# frozen_string_literal: true

module TagPresenter
  def tags_sorted_list(sort_method = 'ASC')
    tags_on(:directions).order("lower(name) #{sort_method}").pluck(:name)
  end
end
