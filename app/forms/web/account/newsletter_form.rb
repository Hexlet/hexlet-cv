# frozen_string_literal: true

class Web::Account::NewsletterForm < User
  include ActiveFormModel

  permit :resume_mail_enabled
end
