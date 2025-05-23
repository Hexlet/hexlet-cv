class RenameDescriptionToConditionsDescription < ActiveRecord::Migration[6.1]
  def change
    rename_column :vacancies, :description, :conditions_description
  end
end
