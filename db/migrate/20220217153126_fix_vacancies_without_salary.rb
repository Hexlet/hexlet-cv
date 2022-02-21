class FixVacanciesWithoutSalary < ActiveRecord::Migration[6.1]
  def change
    Vacancy.find_each do |v|
      should_update = !v.salary_amount_type.depends? && v.salary_to.nil? && v.salary_from.nil?

      next if !should_update

      v.salary_amount_type = :depends

      v.save!
    end
  end
end
