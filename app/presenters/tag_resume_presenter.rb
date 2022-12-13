# frozen_string_literal: true

module TagResumePresenter
  def tags_sorted_list(sort_method = 'ASC')
    Resume.tags_on(:directions).order("lower(name) #{sort_method}").pluck(:name)
  end
end
