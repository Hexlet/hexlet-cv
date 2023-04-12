# frozen_string_literal: true

class Web::Admin::StepForm < Step
  include ActiveFormModel

  fields :name,
         :description,
         :tasks_text,
         :review_needed,
         :direction,
         :locale
end
