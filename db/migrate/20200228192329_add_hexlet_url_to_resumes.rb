class AddHexletUrlToResumes < ActiveRecord::Migration[6.0]
  def change
    add_column :resumes, :hexlet_url, :string
  end
end
