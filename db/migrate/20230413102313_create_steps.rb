class CreateSteps < ActiveRecord::Migration[7.0]
  def change
    create_table :steps do |t|
      t.string :name, null: false
      t.text :description, null: false
      t.text :tasks_text, null: false
      t.boolean :review_needed
      t.string :locale, null: false

      t.timestamps
    end
  end
end
