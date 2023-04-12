# frozen_string_literal: true

class Web::Admin::CareerForm < Career
  include ActiveFormModel

  fields :name,
         :description,
         :slug,
         :locale,
         items_attributes: %i[_destroy id order step_id]
end
