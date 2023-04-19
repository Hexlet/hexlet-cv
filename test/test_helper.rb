# frozen_string_literal: true

if ENV['COVERAGE']
  require 'simplecov'

  SimpleCov.start 'custom rails'
end

ENV['RAILS_ENV'] ||= 'test'
require_relative '../config/environment'
require 'rails/test_help'

OmniAuth.config.test_mode = true
OmniAuth.config.add_mock(
  :github,
  provider: 'github',
  uid: '12345',
  info: { name: 'Github User', email: 'github@github.com' }
)
Rails.application.env_config['omniauth.auth'] = OmniAuth.config.mock_auth[:github]

# rubocop:disable Rails/I18nLocaleAssignment
I18n.locale = ENV.fetch('RAILS_LOCALE', 'ru').to_sym
# rubocop:enable Rails/I18nLocaleAssignment

Rails.application.routes.default_url_options[:locale] = I18n.locale
Rails.application.config.action_mailer.default_url_options[:locale] = I18n.locale

class ActiveSupport::TestCase
  include ActiveJob::TestHelper
  # Run tests in parallel with specified workers
  # TODO: return virtualization after fixing power_assert integration
  # parallelize(workers: :number_of_processors)

  # Setup all fixtures in test/fixtures/*.yml for all tests in alphabetical order.
  fixtures :all

  setup do
    queue_adapter.perform_enqueued_jobs = true

    queue_adapter.perform_enqueued_at_jobs = true
  end

  # Add more helper methods to be used by all tests here...
end

class ActionDispatch::IntegrationTest
  include Devise::Test::IntegrationHelpers
end
