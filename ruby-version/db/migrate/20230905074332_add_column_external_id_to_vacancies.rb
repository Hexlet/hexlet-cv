class AddColumnExternalIdToVacancies < ActiveRecord::Migration[7.0]
  def change
    add_column :vacancies, :external_id, :bigint
  end
end
