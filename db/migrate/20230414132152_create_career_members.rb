class CreateCareerMembers < ActiveRecord::Migration[7.0]
  def change
    create_table :career_members do |t|
      t.references :career, null: false, foreign_key: true
      t.references :user, null: false, foreign_key: true
      t.string :state
      t.datetime :finished_at

      t.timestamps
    end
  end
end
