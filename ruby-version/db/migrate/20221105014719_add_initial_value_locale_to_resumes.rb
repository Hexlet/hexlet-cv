class AddInitialValueLocaleToResumes < ActiveRecord::Migration[6.1]
  def self.change
    Resume.find_each do |resume|
      resume.locale = 'ru'
      resume.save!
    end
  end
end
