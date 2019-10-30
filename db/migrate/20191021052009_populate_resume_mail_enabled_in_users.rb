class PopulateResumeMailEnabledInUsers < ActiveRecord::Migration[6.0]
  def change
    User.find_each do |u|
      u.resume_mail_enabled = true
      u.save!
    end
  end
end
