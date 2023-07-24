class AddColumnStateToEvents < ActiveRecord::Migration[7.0]
  def change
    add_column :events, :state, :string, null: false
  end
end
