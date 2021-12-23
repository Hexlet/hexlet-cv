# frozen_string_literal: true

class Web::Vacancies::SearchForm
  include ActiveModel::Model

  attr_accessor(
    :level,
    :city,
    :direction
  )
end
