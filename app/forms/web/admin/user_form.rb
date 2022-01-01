# frozen_string_literal: true

class Web::Admin::UserForm < User
  include ActiveFormModel

  fields :state_event, :first_name, :last_name, :about
end
