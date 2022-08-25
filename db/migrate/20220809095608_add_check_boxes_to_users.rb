class AddCheckBoxesToUsers < ActiveRecord::Migration[6.1]
  def change
    add_column :users, :check_boxes, :string, array: true
  end
end
