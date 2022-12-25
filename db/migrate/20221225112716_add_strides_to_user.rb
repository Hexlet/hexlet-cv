class AddStridesToUser < ActiveRecord::Migration[6.1]
  def change
    add_column :users, :strides, :string, array: true
  end
end
