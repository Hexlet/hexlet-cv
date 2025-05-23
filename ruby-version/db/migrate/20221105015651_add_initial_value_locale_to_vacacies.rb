class AddInitialValueLocaleToVacacies < ActiveRecord::Migration[6.1]
  def self.change
    Vacancy.find_each do |resume|
      resume.locale = 'ru'
      resume.save!
    end
  end
end
