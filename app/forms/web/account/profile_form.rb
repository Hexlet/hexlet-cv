# frozen_string_literal: true

class Web::Account::ProfileForm < User
  include ActiveFormModel

  fields :first_name, :last_name, :about
end
