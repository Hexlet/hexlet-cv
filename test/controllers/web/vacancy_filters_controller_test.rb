# frozen_string_literal: true

require 'test_helper'

class Web::VacancyFiltersControllerTest < ActionDispatch::IntegrationTest
  test 'should get index' do
    get vacancy_filter_url('tag-php')
    assert_response :success
  end
end
