# frozen_string_literal: true

class Web::Admin::ResumeForm < Resume
  include ActiveFormModel

  attrs = %i[
    state_event
    name
    hexlet_url
    github_url
    contact
    contact_email
    contact_phone
    contact_telegram
    locale
    summary
    skills_description
    awards_description
    english_fluency
  ]

  nested_attrs = {
    educations_attributes: %i[description begin_date end_date current _destroy id],
    works_attributes: %i[company position description begin_date end_date current _destroy id]
  }

  fields(*attrs, **nested_attrs)
end
