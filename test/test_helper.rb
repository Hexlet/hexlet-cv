# frozen_string_literal: true

ENV['RAILS_ENV'] ||= 'test'
require_relative '../config/environment'
require 'rails/test_help'

class ActiveSupport::TestCase
  # Run tests in parallel with specified workers
  parallelize(workers: :number_of_processors)

  # Setup all fixtures in test/fixtures/*.yml for all tests in alphabetical order.
  fixtures :all

  # Add more helper methods to be used by all tests here...
end

class ActionDispatch::IntegrationTest
  include Devise::Test::IntegrationHelpers

  # def login(user)
  #   params = {
  #     new_user: {
  #       email: user.email,
  #       password: 'password',
  #       password_confirmation: 'password'
  #     }
  #   }
  #   open_session do |s|
  #     s.post s.user_session_url, params: params
  #   end
  # end
end
