class ChangeResumeMailEnabledDefaultValue < ActiveRecord::Migration[6.1]
  def up
    change_column_default :users, :resume_mail_enabled, from: nil, to: true
  end

  def down
    change_column_default :users, :resume_mail_enabled, from: true, to: nil
  end
end
