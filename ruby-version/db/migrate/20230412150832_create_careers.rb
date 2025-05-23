class CreateCareers < ActiveRecord::Migration[7.0]
  def change
    create_table :careers do |t|
      t.string :name, null: false
      t.text :description, null: false
      t.string :slug, null: false, index: { unique: true }
      t.string :locale, null: false

      t.timestamps
    end
  end
end
