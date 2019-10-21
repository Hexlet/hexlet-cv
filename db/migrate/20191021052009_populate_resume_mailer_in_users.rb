class PopulateResumeMailerInUsers < ActiveRecord::Migration[6.0]
  def change
    User.find_each do |u|
      u.resume_mailer = true
      u.save!
    end
  end
end
