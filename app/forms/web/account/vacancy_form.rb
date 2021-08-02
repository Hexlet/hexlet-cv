# frozen_string_literal: true

class Web::Account::VacancyForm < Vacancy
  include ActiveFormModel

  permit :title,
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
         :salary_amoun_type,
         :location_of_position,
         :location,
         :employment_type,
         :technology_list

  def city_name=(value)
    processed_value = value ? value.downcase : value
    super(processed_value)
  end
end
