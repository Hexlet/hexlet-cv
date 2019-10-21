class AddResumeMailerToUsers < ActiveRecord::Migration[6.0]
  def change
    add_column :users, :resume_mailer, :boolean
  end
end
