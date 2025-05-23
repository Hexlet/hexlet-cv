# frozen_string_literal: true

class Web::Account::ProfileForm < User
  include ActiveFormModel
  include UserPresenter

  validates :first_name, presence: true, length: { maximum: 40 }
  validates :last_name, presence: true, length: { maximum: 40 }

  fields :first_name, :last_name, :about
end
