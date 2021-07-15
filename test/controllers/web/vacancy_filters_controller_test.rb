# frozen_string_literal: true

require 'test_helper'

class Web::VacancyFiltersControllerTest < ActionDispatch::IntegrationTest
  test 'should get index' do
    get web_vacancy_filters_index_url
    assert_response :success
  end
end
