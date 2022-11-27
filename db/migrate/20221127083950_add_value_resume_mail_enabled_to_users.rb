class AddValueResumeMailEnabledToUsers < ActiveRecord::Migration[6.1]
  def self.change
    User.find_each do |user|
      user.resume_mail_enabled = true
      user.save!
    end
  end
end
