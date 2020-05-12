class AddResumeMailEnabledToUsers < ActiveRecord::Migration[6.0]
  def change
    add_column :users, :resume_mail_enabled, :boolean
  end
end
