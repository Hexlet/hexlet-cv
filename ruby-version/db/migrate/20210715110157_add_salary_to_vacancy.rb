class AddSalaryToVacancy < ActiveRecord::Migration[6.1]
  def change
    add_column :vacancies, :salary, :integer
  end
end
