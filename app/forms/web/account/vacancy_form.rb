# frozen_string_literal: true

class Web::Account::VacancyForm < Vacancy
  include ActiveFormModel

  validates :direction_list, direction_list: true
  validates :direction_list, vacancy_direction_list: true

  fields :title,
         :site,
         :position_level,
         :about_company,
         :about_project,
         :responsibilities_description,
         :experience_description,
         :conditions_description,
         :requirements_description,
         :contact_name,
         :contact_phone,
         :contact_telegram,
         :contact_email,
         :company_name,
         :programming_language,
         :country,
         :city_name,
         :link_for_contact,
         :salary_from,
         :salary_to,
         :salary_currency,
         :salary_amount_type,
         :location_of_position,
         :location,
         :employment_type,
         :technology_list,
         :direction_list

  enumerize :salary_amount_type, in: %w[gross net depends]

  def city_name=(value)
    processed_value = value ? value.downcase.strip : value
    super(processed_value)
  end
end
