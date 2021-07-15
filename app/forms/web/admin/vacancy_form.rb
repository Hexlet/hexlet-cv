# frozen_string_literal: true

class Web::Admin::VacancyForm < Vacancy
  include ActiveFormModel

  permit :title,
         :site,
         :description,
         :contact_name,
         :contact_phone,
         :contact_telegram,
         :company_name,
         :language,
         :country,
         :city_name,
         :link_for_contact,
         :state_event
end
