# frozen_string_literal: true

module ModelHelper
  def states_for_select(model, state_machine = :state)
    model.aasm(state_machine).states.map { |s| [s.human_name, s.name] }
  end

  def state_for_render(model, state_machine = :state, state_name = :default)
    model.aasm(state_machine)
         .states
         .find { |s| s.name == state_name.to_sym }
         .try(:human_name)
  end
end
