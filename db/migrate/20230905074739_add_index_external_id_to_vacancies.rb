class AddIndexExternalIdToVacancies < ActiveRecord::Migration[7.0]
  def change
    add_index :vacancies, :external_id
  end
end
