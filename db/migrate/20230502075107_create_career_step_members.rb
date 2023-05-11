class CreateCareerStepMembers < ActiveRecord::Migration[7.0]
  def change
    create_table :career_step_members do |t|
      t.references :career_step, null: false, foreign_key: true
      t.references :career_member, null: false, foreign_key: true
      t.string :state, null: false

      t.timestamps
    end
  end
end
