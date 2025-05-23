class AddPositionLevelToVacancy < ActiveRecord::Migration[6.1]
  def change
    add_column :vacancies, :position_level, :string
  end
end
