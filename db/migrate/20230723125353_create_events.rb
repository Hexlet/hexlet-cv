class CreateEvents < ActiveRecord::Migration[7.0]
  def change
    create_table :events do |t|
      t.references :user, null: false, foreign_key: true
      t.string :kind, null: false
      t.string :locale, null: false
      t.references :resource, polymorphic: true, null: false

      t.timestamps
    end
  end
end
