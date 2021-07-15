# frozen_string_literal: true

class Web::Account::VacancyForm < Vacancy
  include ActiveFormModel

  permit :title,
         :site,
         :position_level,
         :description,
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
         :location,
         :employment_type,
         :technology_list
end
