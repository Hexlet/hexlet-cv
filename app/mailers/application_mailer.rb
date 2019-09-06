# frozen_string_literal: true

class ApplicationMailer < ActionMailer::Base
  default from: 'support@hexlet.io'
  layout 'mailer'
end
