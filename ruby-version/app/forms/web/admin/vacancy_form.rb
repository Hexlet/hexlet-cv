# frozen_string_literal: true

class Web::Admin::VacancyForm < Vacancy
  include ActiveFormModel
  extend Enumerize

  validates :direction_list, direction_list: true
  validates :direction_list, vacancy_direction_list: true

  fields :title,
         :site,
         :position_level,
         :location_of_position,
         :salary_amount_type,
         :salary_currency,
         :responsibilities_description,
         :experience_description,
         :requirements_description,
         :conditions_description,
         :about_company,
         :about_project,
         :contact_name,
         :contact_phone,
         :contact_telegram,
         :contact_email,
         :company_name,
         :programming_language,
         :country,
         :city_name,
         :link_for_contact,
         :locale,
         :salary_from,
         :salary_to,
         :location,
         :technology_list,
         :direction_list,
         :employment_type,
         :state_event,
         :cancelation_reason

  enumerize :salary_amount_type, in: %w[gross net depends]

  def city_name=(value)
    processed_value = value ? value.downcase.strip : value
    super(processed_value)
  end
end
