# frozen_string_literal: true

class Career::StepMutator
  def self.create(career, params)
    ActiveRecord::Base.transaction do
      @step = Career::Step.new(params)
      @step.save!
      order = career.items.last&.order
      new_order = order.nil? ? 1 : order + 1
      career.items.create!(career_step: @step, order: new_order)
    end

    @step
  end
end
