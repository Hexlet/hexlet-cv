class AddCurrencyToVacancy < ActiveRecord::Migration[6.1]
  def change
    add_column :vacancies, :salary_currency, :string
  end
end
