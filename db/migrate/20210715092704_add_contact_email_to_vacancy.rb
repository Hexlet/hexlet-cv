class AddContactEmailToVacancy < ActiveRecord::Migration[6.1]
  def change
    add_column :vacancies, :contact_email, :string
  end
end
