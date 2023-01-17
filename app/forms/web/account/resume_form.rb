# frozen_string_literal: true

class Web::Account::ResumeForm < Resume
  include ActiveFormModel

  attrs = %i[name hexlet_url github_url contact summary skills_description awards_description english_fluency city relocation skill_list direction_list]
  nested_attrs = {
    educations_attributes: %i[description begin_date end_date current _destroy id],
    works_attributes: %i[company position description begin_date end_date current _destroy id]
  }

  fields(*attrs, **nested_attrs)
end
