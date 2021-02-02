class AddContactToResume < ActiveRecord::Migration[6.0]
  def change
    add_column :resumes, :contact, :string
  end
end
