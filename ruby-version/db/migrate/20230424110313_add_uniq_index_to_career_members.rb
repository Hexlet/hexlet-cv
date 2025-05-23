class AddUniqIndexToCareerMembers < ActiveRecord::Migration[7.0]
  def change
    add_index :career_members, %i[user_id career_id], unique: true, where: "state = 'active'"
  end
end
