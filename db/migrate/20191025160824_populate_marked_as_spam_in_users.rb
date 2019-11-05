class PopulateMarkedAsSpamInUsers < ActiveRecord::Migration[6.0]
  def change
    User.find_each do |u|
      u.marked_as_spam = false
      u.save!
    end
  end
end
