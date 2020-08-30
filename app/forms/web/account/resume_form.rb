# frozen_string_literal: true

class Web::Account::ResumeForm < Resume
  include ActiveFormModel

  attrs = %i[name hexlet_url github_url summary skills_description awards_description english_fluency]
  nested_attrs = {
    educations_attributes: %i[description begin_date end_date current _destroy id],
    works_attributes: %i[company position description begin_date end_date current _destroy id]
  }

  permit(*attrs, **nested_attrs)
end
