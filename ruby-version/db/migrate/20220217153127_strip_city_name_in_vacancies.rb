class StripCityNameInVacancies < ActiveRecord::Migration[6.1]
  def change
    Vacancy.find_each do |v|
      next unless v.city_name?

      v.city_name = v.city_name.strip
      v.save!
    end
  end
end
