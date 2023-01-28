# frozen_string_literal: true

ruby '3.2.0'

source 'https://rubygems.org'
git_source(:github) { |repo| "https://github.com/#{repo}.git" }

gem 'acts-as-taggable-on'

# Bundle edge Rails instead: gem 'rails', github: 'rails/rails'
# NOTE Не обновил версию рельсы, т.к. https://github.com/charlotte-ruby/impressionist/issues/302
gem 'rails', '~> 7'
# Use Puma as the app server
gem 'puma'
# Use SCSS for stylesheets
# gem 'sass-rails'
# Build JSON APIs with ease. Read more: https://github.com/rails/jbuilder
gem 'jbuilder'
# Use Redis adapter to run Action Cable in production
gem 'redis'
# Use Active Model has_secure_password
# gem 'bcrypt', '~> 3.1.7'

# Use Active Storage variant
# gem 'image_processing', '~> 1.2'

gem 'bootstrap'
gem 'jquery-rails'
gem 'sorbet'
gem 'sprockets-rails'
gem 'terser'
# Reduces boot times through caching; required in config/boot.rb
gem 'bootsnap', require: false
gem 'counter_culture'
gem 'dotenv-rails'
gem 'net-imap', require: false
gem 'net-pop', require: false
gem 'net-smtp', require: false
gem 'wicked_pdf'
gem 'wkhtmltopdf-binary'

group :development, :test do
  # Call 'byebug' anywhere in the code to stop execution and get a debugger console
  gem 'byebug', platforms: %i[mri mingw x64_mingw]
  gem 'faker'
  # Use sqlite3 as the database for Active Record
  gem 'factory_bot_rails'
  gem 'rubocop-performance'
  gem 'rubocop-rails'
  gem 'slim_lint'
  gem 'sqlite3'
end

group :development do
  gem 'yard'
  # Access an interactive console on exception pages or by calling 'console' anywhere in the code.
  gem 'html2slim'
  gem 'i18n-debug'
  gem 'listen'
  gem 'web-console'
  # Spring speeds up development by keeping your application running in the background. Read more: https://github.com/rails/spring
  gem 'reek'
  gem 'rubocop'
  gem 'solargraph'
  gem 'spring'
end

group :test do
  # Adds support for Capybara system testing and selenium driver
  gem 'capybara'
  gem 'minitest-power_assert'
  gem 'selenium-webdriver'
  gem 'simplecov', require: false
  # Easy installation and use of web drivers to run system tests with browsers
  gem 'webdrivers'
end

# Windows does not include zoneinfo files, so bundle the tzinfo-data gem
gem 'tzinfo-data', platforms: %i[mingw mswin x64_mingw jruby]

gem 'simple_form'

gem 'slim-rails'

gem 'aasm'

group :production do
  gem 'pg'
end

gem 'active_form_model', '~> 0.4.1'
gem 'browser'
gem 'cocoon'
gem 'devise'
gem 'devise-bootstrap-views'
gem 'devise-i18n'
gem 'enumerize'
gem 'flash_rails_messages'
gem 'geocoder'
gem 'gon'
gem 'impressionist'
gem 'kaminari'
gem 'meta-tags'
gem 'omniauth'
gem 'omniauth-github'
gem 'omniauth-rails_csrf_protection'
gem 'paint'
gem 'paper_trail'
gem 'pundit'
gem 'rails-i18n'
gem 'ransack'
gem 'recaptcha'
gem 'redcarpet'
gem 'rollbar' # TODO: switch to sentry
gem 'translit'
gem 'validate_url'
gem 'valid_email2'
