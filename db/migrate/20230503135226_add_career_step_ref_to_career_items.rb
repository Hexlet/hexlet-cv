class AddCareerStepRefToCareerItems < ActiveRecord::Migration[7.0]
  def change
    add_reference :career_items, :career_step, null: false, foreign_key: true
  end
end
