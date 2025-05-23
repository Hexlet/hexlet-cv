class AddNotNullConstrainColumnNameToResume < ActiveRecord::Migration[7.0]
  def change
    change_column_null :resumes, :name, false
  end
end
