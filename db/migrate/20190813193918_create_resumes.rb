class CreateResumes < ActiveRecord::Migration[6.0]
  def change
    create_table :resumes do |t|
      t.string :link
      t.string :state
      t.text :resume
      t.references :user, null: false, foreign_key: true

      t.timestamps
    end
  end
end
