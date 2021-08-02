class AddAboutProjectToVacancies < ActiveRecord::Migration[6.1]
  def change
    add_column :vacancies, :about_project, :string
  end
end
