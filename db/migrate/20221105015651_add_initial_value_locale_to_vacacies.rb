class AddInitialValueLocaleToVacacies < ActiveRecord::Migration[6.1]
  def self.change
    Vacancy.all.find_each do |resume|
      resume.locale = 'ru'
      resume.save
    end
  end
end
