class ChangeColumnNameCheckboxes < ActiveRecord::Migration[6.1]
  def change
    rename_column :users, :check_boxes, :profile_checkboxes
  end
end
