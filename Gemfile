# frozen_string_literal: true

source 'https://rubygems.org'
git_source(:github) { |repo| "https://github.com/#{repo}.git" }

# Bundle edge Rails instead: gem 'rails', github: 'rails/rails'
gem 'rails'
# Use Puma as the app server
gem 'puma'
# Use SCSS for stylesheets
gem 'sass-rails'
# Transpile app-like JavaScript. Read more: https://github.com/rails/webpacker
gem 'webpacker'
# Build JSON APIs with ease. Read more: https://github.com/rails/jbuilder
gem 'jbuilder'
# Use Redis adapter to run Action Cable in production
# gem 'redis', '~> 4.0'
# Use Active Model has_secure_password
# gem 'bcrypt', '~> 3.1.7'

# Use Active Storage variant
# gem 'image_processing', '~> 1.2'

# Reduces boot times through caching; required in config/boot.rb
gem 'bootsnap', '>= 1.4.2', require: false
gem 'dotenv-rails'

group :development, :test do
  # Call 'byebug' anywhere in the code to stop execution and get a debugger console
  gem 'byebug', platforms: %i[mri mingw x64_mingw]
  gem 'faker'
  # Use sqlite3 as the database for Active Record
  gem 'factory_bot_rails'
  gem 'rubocop-performance'
  gem 'rubocop-rails'
  gem 'sqlite3'
end

group :development do
  gem 'yard'
  # Access an interactive console on exception pages or by calling 'console' anywhere in the code.
  gem 'i18n-debug'
  gem 'listen'
  gem 'web-console'
  # Spring speeds up development by keeping your application running in the background. Read more: https://github.com/rails/spring
  gem 'rubocop'
  gem 'solargraph'
  gem 'spring'
  gem 'spring-watcher-listen'
end

group :test do
  # Adds support for Capybara system testing and selenium driver
  gem 'capybara'
  gem 'minitest-power_assert'
  gem 'selenium-webdriver'
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

gem 'kaminari'

gem 'rails-i18n'

gem 'paper_trail', '~> 10.3'

gem 'gon', '~> 6.2'

gem 'enumerize', '~> 2.3'

gem 'devise', '~> 4.7'

gem 'devise-i18n', '~> 1.8'

gem 'flash_rails_messages', '~> 2.1'

gem 'devise-bootstrap-views', '~> 1.1'

gem 'rollbar', '~> 2.22'

gem 'redcarpet', '~> 3.5'

gem 'cocoon', '~> 1.2'

gem 'paint', '~> 2.1'
