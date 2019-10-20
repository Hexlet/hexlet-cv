class PopulateMailerStateInUsers < ActiveRecord::Migration[6.0]
  def change
    User.find_each do |u|
      u.mailer_state = 'subscriber'
      u.save!
    end
  end
end
