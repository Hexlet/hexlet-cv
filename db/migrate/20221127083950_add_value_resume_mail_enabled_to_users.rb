class AddValueResumeMailEnabledToUsers < ActiveRecord::Migration[6.1]
  def self.change
    # rubocop:todo Rails/SkipsModelValidations
    User.where(resume_mail_enabled: nil).update_all(resume_mail_enabled: true)
    # rubocop:enable Rails/SkipsModelValidations
  end
end
