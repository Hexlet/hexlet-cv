class AddUniqIndexToCareerItems < ActiveRecord::Migration[7.0]
  def change
    add_index :career_items, %i[career_id step_id], unique: true
  end
end
