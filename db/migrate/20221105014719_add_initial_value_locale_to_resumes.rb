class AddInitialValueLocaleToResumes < ActiveRecord::Migration[6.1]
  def self.change
    Resume.all.find_each do |resume|
      resume.locale = 'ru'
      resume.save
    end
  end
end
