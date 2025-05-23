class AddMarkedAsSpamToUsers < ActiveRecord::Migration[6.0]
  def change
    add_column :users, :marked_as_spam, :boolean
  end
end
