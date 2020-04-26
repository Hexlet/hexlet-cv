class AddAdminStateToUsers < ActiveRecord::Migration[6.0]
  def change
    add_column :users, :admin_state, :string
  end
end
