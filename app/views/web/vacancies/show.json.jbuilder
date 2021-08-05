# frozen_string_literal: true

json.id @vacancy.id
json.title @vacancy.title
json.programming_language = @vacancy.programming_language
json.location = @vacancy.location
json.site @vacancy.site
json.city_name = @vacancy.city_name
json.salary_from @vacancy.salary_from
json.salary_to @vacancy.salary_to
json.salary_currency @vacancy.salary_currency
json.location_of_position @vacancy.location_of_position.text
json.technology_list = @vacancy.technology_list
json.salary_amount_type_text @vacancy.salary_amount_type.text
json.country @vacancy.country
json.about_company @vacancy.about_company
json.about_project @vacancy.about_project
json.contact_email @vacancy.contact_email
json.contact_name @vacancy.contact_name
json.contact_telegram @vacancy.contact_telegram
json.contact_phone @vacancy.contact_phone
json.link_for_contact @vacancy.link_for_contact
json.position_level_text @vacancy.position_level.text
json.employment_type_text @vacancy.employment_type.text
json.company_name @vacancy.company_name
json.responsibilities_description @vacancy.responsibilities_description
json.experience_description @vacancy.experience_description
json.conditions_description @vacancy.conditions_description
json.requirements_description @vacancy.requirements_description
