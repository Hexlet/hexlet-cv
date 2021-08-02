class AddAboutCompanyToVacancies < ActiveRecord::Migration[6.1]
  def change
    add_column :vacancies, :about_company, :string
  end
end
