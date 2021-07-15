# frozen_string_literal: true

class Web::Admin::VacancyForm < Vacancy
  include ActiveFormModel

  permit :title,
         :site,
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
         :technology_list,
         :employment_type,
         :state_event
end
