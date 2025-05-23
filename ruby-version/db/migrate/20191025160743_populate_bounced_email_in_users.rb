class PopulateBouncedEmailInUsers < ActiveRecord::Migration[6.0]
  def change
    User.find_each do |u|
      u.bounced_email = false
      u.save!
    end
  end
end
