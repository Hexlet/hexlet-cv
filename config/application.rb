# frozen_string_literal: true

require_relative 'boot'

require 'active_model/railtie'
require 'active_job/railtie'
require 'active_record/railtie'
require 'active_storage/engine'
require 'action_controller/railtie'
require 'action_mailer/railtie'
require 'action_mailbox/engine'
require 'action_text/engine'
require 'action_view/railtie'
require 'action_cable/engine'
# require 'sprockets/railtie'
require 'rails/test_unit/railtie'

# Require the gems listed in Gemfile, including any gems
# you've limited to :test, :development, or :production.
Bundler.require(*Rails.groups)

Dotenv::Railtie.load

module HexletCv
  class Application < Rails::Application
    # Initialize configuration defaults for originally generated Rails version.
    config.load_defaults 6.1
    config.exceptions_app = routes

    # https://docs.rubocop.org/rubocop-rails/cops_rails.html#railsenvironmentvariableaccess
    config.vars = {
      ga: ENV['GOOGLE_ANALYTICS_KEY'],
      gtm: ENV['GOOGLE_TAG_MANAGER_KEY'],
      email_from: ENV['EMAIL_FROM']
    }

    # Settings in config/environments/* take precedence over those specified here.
    # Application configuration can go into files in config/initializers
    # -- all .rb files in that directory are automatically loaded after loading
    # the framework and any gems in your application.
    config.i18n.default_locale = :ru
    config.generators do |g|
      g.assets false
    end
  end
end
