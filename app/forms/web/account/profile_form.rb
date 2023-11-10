# frozen_string_literal: true

class Web::Account::ProfileForm < User
  include ActiveFormModel
  include UserPresenter

  validates :first_name, :last_name, presence: true

  fields :first_name, :last_name, :about
end
