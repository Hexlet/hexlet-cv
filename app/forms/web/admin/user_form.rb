# frozen_string_literal: true

class Web::Admin::UserForm < User
  include ActiveFormModel
  include UserPresenter

  validates :last_name, :first_name, presence: true

  fields :state_event,
         :role,
         :first_name,
         :last_name,
         :about
end
