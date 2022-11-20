class AddColumnRelocationToResumes < ActiveRecord::Migration[6.1]
  def change
    add_column :resumes, :relocation, :string
  end
end
