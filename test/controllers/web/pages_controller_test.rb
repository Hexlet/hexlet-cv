# frozen_string_literal: true

require 'test_helper'

class Web::PagesControllerTest < ActionDispatch::IntegrationTest
  test 'should get about' do
    get page_path(id: 'about')
    assert_response :success
  end
end
