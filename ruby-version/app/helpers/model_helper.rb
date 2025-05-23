# frozen_string_literal: true

module ModelHelper
  def states_for_select(model, state_machine = :state)
    model.aasm(state_machine).states.map { |s| [s.human_name, s.name] }
  end
end
