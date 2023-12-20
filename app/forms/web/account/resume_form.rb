# frozen_string_literal: true

class Web::Account::ResumeForm < Resume
  include ActiveFormModel

  validates :direction_list, direction_list: true

  attrs = %i[
    name
    github_url
    contact
    contact_email
    contact_phone
    contact_telegram summary
    skills_description
    summary
    english_fluency
    city
    relocation
    skill_list
    direction_list
    about_myself
    projects_description
  ]

  nested_attrs = {
    educations_attributes: %i[description begin_date end_date current _destroy id],
    works_attributes: %i[company position description begin_date end_date current _destroy id]
  }

  fields(*attrs, **nested_attrs)
end
