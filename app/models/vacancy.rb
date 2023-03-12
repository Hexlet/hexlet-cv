# frozen_string_literal: true

class Vacancy < ApplicationRecord
  include StateConcern
  extend Enumerize
  extend TagVacancyPresenter
  include VacancyRepository
  include VacancyPresenter

  acts_as_taggable_on :technologies, :directions
  enumerize :employment_type, in: EMPLOYMENT_TYPES, default: 'full-time', predicates: true, scope: true
  enumerize :position_level, in: POSITION_LEVELS, default: 'junior', predicates: true, scope: true
  enumerize :salary_currency, in: %w[rub usd eur], default: 'rub'
  enumerize :salary_amount_type, in: %w[gross net depends], default: 'net'
  enumerize :location_of_position, in: %w[remote onsite hybrid], default: 'onsite'
  enumerize :locale, in: %i[en ru], default: :ru
  # enumerize :programming_language, in: PROGRAMMING_LANGAUGES, default: 'full-time', predicates: true, scope: true
  # enumerize :country_name, in: COUNTRIES, default: :user, predicates: true, scope: true

  validates :title, presence: true
  validates :contact_email, 'valid_email_2/email': true
  validates :company_name, presence: true
  validates :responsibilities_description, presence: true
  validates :site, url: true
  validates :link_for_contact, url: { allow_blank: true }
  validates :position_level, presence: true
  validates :employment_type, presence: true
  validates :salary_from, presence: true,
                          numericality: { only_integer: true, greater_than: 0 },
                          unless: -> { salary_amount_type == :depends || salary_to&.positive? }
  validates :salary_to, presence: true,
                        numericality: { only_integer: true, greater_than: 0 },
                        unless: -> { salary_amount_type == :depends || salary_from&.positive? }
  validates :salary_currency, presence: true
  # validates :programming_language, presence: true

  belongs_to :creator, class_name: 'User'
  belongs_to :country, optional: true

  aasm :state, column: :state, timestamps: true do
    state :idle
    state :on_moderate
    state :published
    state :archived

    event :publish do
      transitions to: :published
    end

    event :send_to_moderate do
      transitions from: :idle, to: :on_moderate
    end

    event :archive do
      transitions to: :archived
    end
  end

  def salary?
    salary_from? || salary_to?
  end

  def to_s
    title
  end

  def self.ransackable_attributes(_auth_object = nil)
    %w[id title state name company_name created_at]
  end

  def self.ransackable_associations(_auth_object = nil)
    %w[technologies user]
  end
end
