class AddUniqIndexToCareerMembers < ActiveRecord::Migration[7.0]
  def change
    add_index :career_members, %i[career_id user_id], unique: true, where: "state = 'active'"
  end
end
