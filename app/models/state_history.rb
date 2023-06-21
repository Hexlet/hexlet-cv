# frozen_string_literal: true

class StateHistory < ApplicationRecord
  belongs_to :stateable, polymorphic: true
end
