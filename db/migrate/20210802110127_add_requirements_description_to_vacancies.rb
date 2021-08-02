class AddRequirementsDescriptionToVacancies < ActiveRecord::Migration[6.1]
  def change
    add_column :vacancies, :requirements_description, :string
  end
end
