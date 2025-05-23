class AddSalaryAmountType < ActiveRecord::Migration[6.1]
  def change
    add_column :vacancies, :salary_amount_type, :string
  end
end
