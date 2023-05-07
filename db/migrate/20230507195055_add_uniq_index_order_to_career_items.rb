class AddUniqIndexOrderToCareerItems < ActiveRecord::Migration[7.0]
  def change
    add_index :career_items, %i[career_id order], unique: true
  end
end
