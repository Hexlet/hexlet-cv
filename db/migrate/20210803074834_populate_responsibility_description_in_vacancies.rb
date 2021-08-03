class PopulateResponsibilityDescriptionInVacancies < ActiveRecord::Migration[6.1]
  def change
    Vacancy.find_each do |v|
      next if v.responsibilities_description?

      v.responsibilities_description = v.conditions_description
      v.conditions_description = ''
      v.save!
    end
  end
end
