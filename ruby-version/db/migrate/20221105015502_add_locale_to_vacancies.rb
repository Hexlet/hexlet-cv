class AddLocaleToVacancies < ActiveRecord::Migration[6.1]
  def change
    add_column :vacancies, :locale, :string
  end
end
