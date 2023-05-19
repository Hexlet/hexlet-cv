# frozen_string_literal: true

class Career < ApplicationRecord
  extend Enumerize
  include CareerRepository

  validates :name, :description, :locale, presence: true
  validates :slug, presence: true, slug: true, uniqueness: { case_sensitive: false }
  has_many :items, class_name: 'Career::Item', dependent: :destroy
  has_many :steps, through: :items, source: :career_step
  has_many :members, class_name: 'Career::Member', dependent: :destroy
  has_many :users, through: :members

  accepts_nested_attributes_for :items, reject_if: :all_blank, allow_destroy: true
  enumerize :locale, in: I18n.available_locales

  def self.ransackable_attributes(_auth_object = nil)
    %w[created_at direction locale name slug]
  end
end
