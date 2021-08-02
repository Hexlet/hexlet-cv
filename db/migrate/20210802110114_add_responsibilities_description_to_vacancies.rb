class AddResponsibilitiesDescriptionToVacancies < ActiveRecord::Migration[6.1]
  def change
    add_column :vacancies, :responsibilities_description, :string
  end
end
