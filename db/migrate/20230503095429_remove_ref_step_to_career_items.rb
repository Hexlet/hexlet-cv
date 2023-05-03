class RemoveRefStepToCareerItems < ActiveRecord::Migration[7.0]
  def change
    remove_reference(:career_items, :step, foreign_key: true)
  end
end
