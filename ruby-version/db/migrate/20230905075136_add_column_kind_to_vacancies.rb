class AddColumnKindToVacancies < ActiveRecord::Migration[7.0]
  def change
    add_column :vacancies, :kind, :string
  end
end
