class RemoveIndexFromCareerStepMembers < ActiveRecord::Migration[7.0]
  def change
    remove_index :career_step_members, :career_member_id
  end
end
