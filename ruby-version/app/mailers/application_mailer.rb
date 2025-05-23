# frozen_string_literal: true

class ApplicationMailer < ActionMailer::Base
  default from: Rails.application.config.vars[:email_from]
  layout 'mailer'
end
