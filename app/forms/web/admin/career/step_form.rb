# frozen_string_literal: true

class Web::Admin::Career::StepForm < Career::Step
  include ActiveFormModel

  fields :name,
         :description,
         :tasks_text,
         :review_needed,
         :direction,
         :locale,
         :slug
end
