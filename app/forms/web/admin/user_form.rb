# frozen_string_literal: true

class Web::Admin::UserForm < User
  include ActiveFormModel
  include UserPresenter

  validates :first_name, presence: true, length: { maximum: 40 }
  validates :last_name, presence: true, length: { maximum: 40 }

  fields :state_event,
         :role,
         :first_name,
         :last_name,
         :about
end
