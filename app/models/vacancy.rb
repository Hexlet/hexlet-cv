# frozen_string_literal: true

# == Schema Information
#
# Table name: vacancies
#
#  id                           :integer          not null, primary key
#  about_company                :string
#  about_project                :string
#  cancelation_reason           :string
#  city_name                    :string
#  company_name                 :string
#  conditions_description       :text
#  contact_email                :string
#  contact_name                 :string
#  contact_phone                :string
#  contact_telegram             :string
#  employment_type              :string
#  experience_description       :string
#  kind                         :string           not null
#  link_for_contact             :string
#  locale                       :string
#  location                     :string
#  location_of_position         :string
#  position_level               :string
#  programming_language         :string
#  published_at                 :datetime
#  requirements_description     :string
#  responsibilities_description :string
#  salary_amount_type           :string
#  salary_currency              :string
#  salary_from                  :integer
#  salary_to                    :integer
#  site                         :string
#  state                        :string
#  title                        :string
#  created_at                   :datetime         not null
#  updated_at                   :datetime         not null
#  country_id                   :integer
#  creator_id                   :integer          not null
#  external_id                  :integer
#
# Indexes
#
#  index_vacancies_on_country_id   (country_id)
#  index_vacancies_on_creator_id   (creator_id)
#  index_vacancies_on_external_id  (external_id)
#
# Foreign Keys
#
#  country_id  (country_id => countries.id)
#  creator_id  (creator_id => users.id)
#
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
  enumerize :kind, in: %i[hr habr], predicates: true, scope: true
  enumerize :cancelation_reason, in: %i[high_requirements stack_irrelevant low_wages vacancy_competitors education_requires], predicates: true, scope: true
  # Ex:- scope :active, -> {where(:active => true)}
  # enumerize :programming_language, in: PROGRAMMING_LANGAUGES, default: 'full-time', predicates: true, scope: true
  # enumerize :country_name, in: COUNTRIES, default: :user, predicates: true, scope: true

  validates :title, presence: true
  validates :kind, presence: true
  validates :contact_email, 'valid_email_2/email': true
  validates :company_name, presence: true
  validates :responsibilities_description, presence: true
  validates :site, presence: true, url: true
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
  validates :cancelation_reason, presence: true, if: -> { canceled? || state_event == 'cancel' }

  belongs_to :creator, class_name: 'User'
  belongs_to :country, optional: true

  aasm :state, column: :state, timestamps: true do
    state :idle
    state :on_moderate
    state :published
    state :archived
    state :canceled

    event :publish do
      transitions to: :published
    end

    event :send_to_moderate do
      transitions from: %i[idle canceled], to: :on_moderate
    end

    event :archive do
      transitions to: :archived
    end

    event :restore do
      transitions from: %i[archived], to: :on_moderate
    end

    event :cancel do
      transitions from: %i[on_moderate], to: :canceled
    end
  end

  def initialize(attribute = nil)
    defaults = {
      locale: I18n.locale,
      kind: :hr
    }

    attrs_with_defaults = attribute ? defaults.merge(attribute) : defaults
    super(attrs_with_defaults)
  end

  def salary?
    salary_from? || salary_to?
  end

  def to_s
    title
  end

  def self.ransackable_attributes(_auth_object = nil)
    %w[id kind title state name company_name created_at published_at]
  end

  def self.ransackable_associations(_auth_object = nil)
    %w[technologies creator]
  end

  def need_escape_html?
    !habr?
  end
end
