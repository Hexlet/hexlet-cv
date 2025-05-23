class AddConstrainNotNullContactEmailFromResume < ActiveRecord::Migration[7.0]
  def change
    change_column_null :resumes, :contact_email, false
  end
end
