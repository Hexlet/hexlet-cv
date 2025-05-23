class FinishedOldStepByFeycot < ActiveRecord::Migration[7.0]
  def change
    step_member = Career::Step::Member.find_by(id: 42)

    return unless step_member

    step_member.finish!
  end
end
