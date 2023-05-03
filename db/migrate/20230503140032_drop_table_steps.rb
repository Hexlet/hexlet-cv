class DropTableSteps < ActiveRecord::Migration[7.0]
  def change
    drop_table :steps
  end
end
