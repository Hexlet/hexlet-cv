# frozen_string_literal: true

class Vacancy < ApplicationRecord
  include StateConcern
  extend Enumerize
  include VacancyRepository

  validates :title, presence: true
  validates :company_name, presence: true
  validates :description, presence: true
  validates :site, presence: true
  validates :language, presence: true

  belongs_to :creator, class_name: 'User'
  belongs_to :country, optional: true

  aasm :state, column: :state do
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

  # TODO: move to presenter
  def location
    city_name? ? [city_name, country].join(', ') : I18n.t('remote_job')
  end

  def to_s
    title
  end
end
