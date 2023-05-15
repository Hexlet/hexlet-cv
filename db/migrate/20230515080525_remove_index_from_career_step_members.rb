class RemoveIndexFromCareerStepMembers < ActiveRecord::Migration[7.0]
  def change
    remove_index :career_step_members, :career_step_id
  end
end
