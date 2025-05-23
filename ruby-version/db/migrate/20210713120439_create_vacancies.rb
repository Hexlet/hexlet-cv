class CreateVacancies < ActiveRecord::Migration[6.1]
  def change
    create_table :vacancies do |t|
      t.references :creator, null: false, foreign_key: { to_table: :users }
      t.string :state
      t.string :title
      t.string :language
      t.string :location
      t.string :company
      t.string :site
      t.string :contact_name
      t.string :contact_telegram
      t.string :contact_phone
      t.string :description

      t.timestamps
    end
  end
end
