class CreateUsers < ActiveRecord::Migration[6.0]
  def change
    create_table :users do |t|
      t.string :email
      t.string :password_digest
      t.string :first_name
      t.string :last_name

      t.timestamps
    end
    add_index :users, :email, unique: true
  end
end
