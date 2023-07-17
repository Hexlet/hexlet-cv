# frozen_string_literal: true

class Web::LeadForm < Lead
  include ActiveFormModel

  fields :user_name, :phone_number, :email
end
