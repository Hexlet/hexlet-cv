# frozen_string_literal: true

class Web::Account::ProfileForm < User
  include ActiveFormModel

  permit :first_name, :last_name, :about
end
