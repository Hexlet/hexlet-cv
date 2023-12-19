class UpdateContactEmailFromResume < ActiveRecord::Migration[7.0]
  def change
    Resume.includes(:user).where(contact_email: nil).find_each do |resume|
      resume.contact_email = resume.user.email
      resume.save!
    end
  end
end
