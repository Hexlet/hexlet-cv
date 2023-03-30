class InitValueLocaleToUser < ActiveRecord::Migration[7.0]
  def self.change
    User.find_each do |user|
      user.locale = 'ru'
      user.save!
    end
  end
end
