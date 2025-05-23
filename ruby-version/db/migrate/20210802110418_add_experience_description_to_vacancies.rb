class AddExperienceDescriptionToVacancies < ActiveRecord::Migration[6.1]
  def change
    add_column :vacancies, :experience_description, :string
  end
end
