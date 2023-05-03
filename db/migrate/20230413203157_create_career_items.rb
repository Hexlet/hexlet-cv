class CreateCareerItems < ActiveRecord::Migration[7.0]
  def change
    create_table :career_items do |t|
      t.integer :order
      t.references :career, null: false, foreign_key: true
      t.references :step, null: false, foreign_key: true

      t.timestamps
    end
  end
end
