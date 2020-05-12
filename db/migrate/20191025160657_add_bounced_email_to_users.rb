class AddBouncedEmailToUsers < ActiveRecord::Migration[6.0]
  def change
    add_column :users, :bounced_email, :boolean
  end
end
