class UpdateKindForVacancies < ActiveRecord::Migration[7.0]
  def change
    Vacancy.find_each do |vacancy|
      if !vacancy.kind && vacancy.valid?
        vacancy.kind = :hr
        vacancy.save!
      end
    end
  end
end
