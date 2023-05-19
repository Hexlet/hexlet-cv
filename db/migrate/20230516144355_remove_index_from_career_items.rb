class RemoveIndexFromCareerItems < ActiveRecord::Migration[7.0]
  def change
    remove_index :career_items, :career_id
  end
end
