class AddUniqIndexToCareerStepMembers < ActiveRecord::Migration[7.0]
  def change
    add_index :career_step_members, %i[career_member_id career_step_id], unique: true
  end
end
