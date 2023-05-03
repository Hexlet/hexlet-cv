class RemoveUniqIndexToCareerItems < ActiveRecord::Migration[7.0]
  def change
    remove_index :career_items, %i[career_id step_id]
  end
end
