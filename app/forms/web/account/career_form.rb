# frozen_string_literal: true

class Web::Account::CareerForm < User
  include ActiveFormModel

  fields strides: []

  def strides=(value)
    super(value & Career::GOALS)
  end
end
