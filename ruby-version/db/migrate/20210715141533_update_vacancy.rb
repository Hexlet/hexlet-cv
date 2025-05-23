class UpdateVacancy < ActiveRecord::Migration[6.1]
  def change
    rename_column :vacancies, :salary, :salary_from
    rename_column :vacancies, :language, :programming_language
    add_column :vacancies, :salary_to, :integer
    add_column :vacancies, :employment_type, :string
  end
end
