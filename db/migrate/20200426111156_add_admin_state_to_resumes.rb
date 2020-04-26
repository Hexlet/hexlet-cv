class AddAdminStateToResumes < ActiveRecord::Migration[6.0]
  def change
    add_column :resumes, :admin_state, :string
  end
end
