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
  def slug=(value)
    if value && new_record?
      write_attribute(:slug, value.parameterize)
    elsif value && !new_record?
      write_attribute(:slug, slug)
    else
      super
    end
  end
end
