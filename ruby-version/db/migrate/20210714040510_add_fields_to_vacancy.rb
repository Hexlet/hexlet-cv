class AddFieldsToVacancy < ActiveRecord::Migration[6.1]
  def change
    change_column :vacancies, :description, :text
    rename_column :vacancies, :company, :company_name
    add_column :vacancies, :city_name, :string
    add_reference :vacancies, :country, foreign_key: true
  end
end
