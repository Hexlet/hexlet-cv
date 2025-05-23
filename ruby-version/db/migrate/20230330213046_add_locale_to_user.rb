class AddLocaleToUser < ActiveRecord::Migration[7.0]
  def change
    add_column :users, :locale, :string
  end
end
