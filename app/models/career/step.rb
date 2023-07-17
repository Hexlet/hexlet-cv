# frozen_string_literal: true

class Career::Step < ApplicationRecord
  extend Enumerize
  include Career::StepRepository

  enumerize :locale, in: I18n.available_locales
  enumerize :notification_kind, in: %i[next_step_open_source], predicates: true

  validates :name, :description, :tasks_text, :locale, presence: true

  has_many :career_items, class_name: 'Career::Item', inverse_of: :career_step, foreign_key: 'career_step_id', dependent: :destroy
  has_many :careers, through: :career_items
  has_many :career_step_members, class_name: 'Career::Step::Member', inverse_of: :career_step, foreign_key: 'career_step_id', dependent: :destroy

  def self.ransackable_attributes(_auth_object = nil)
    %w[created_at direction locale name]
  end
end
