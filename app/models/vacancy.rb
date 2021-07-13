# frozen_string_literal: true

class Vacancy < ApplicationRecord
  belongs_to :creator, class_name: 'User'
end
