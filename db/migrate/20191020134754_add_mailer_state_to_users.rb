class AddMailerStateToUsers < ActiveRecord::Migration[6.0]
  def change
    add_column :users, :mailer_state, :string
  end
end
