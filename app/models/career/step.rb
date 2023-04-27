# frozen_string_literal: true

class Career::Step < ApplicationRecord
  extend Enumerize

  enumerize :locale, in: I18n.available_locales
  validates :name, :description, :tasks_text, :locale, presence: true

  has_many :career_items, class_name: 'Career::Item', inverse_of: :career_step, foreign_key: 'career_step_id', dependent: :destroy
  has_many :careers, through: :career_items

  def self.ransackable_attributes(_auth_object = nil)
    %w[created_at direction locale name]
  end
end
