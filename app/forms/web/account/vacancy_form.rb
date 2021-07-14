# frozen_string_literal: true

class Web::Account::VacancyForm < Vacancy
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
         :city_name
end
