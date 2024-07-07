class AddColumnCancelationReasonToVacancy < ActiveRecord::Migration[7.1]
  def change
    add_column :vacancies, :cancelation_reason, :string
  end
end
