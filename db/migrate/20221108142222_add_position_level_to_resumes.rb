class AddPositionLevelToResumes < ActiveRecord::Migration[6.1]
  def change
    add_column :resumes, :position_level, :string
  end
end
