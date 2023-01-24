# frozen_string_literal: true

class ApplicationMailer < ActionMailer::Base
  default from: Rails.application.config.vars[:email_from]
  layout 'mailer'
end

Rails.application.configure do
  routes.default_url_options[:locale] = :en
  config.action_mailer.default_url_options[:locale] = :en
end
