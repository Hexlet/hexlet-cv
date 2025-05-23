class AddLocationOfPositionToVacancies < ActiveRecord::Migration[6.1]
  def change
    add_column :vacancies, :location_of_position, :string
  end
end
