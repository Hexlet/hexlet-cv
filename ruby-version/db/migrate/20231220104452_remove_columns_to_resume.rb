class RemoveColumnsToResume < ActiveRecord::Migration[7.0]
  def change
    remove_column :resumes, :about_my_self, :text
    remove_column :resumes, :project_descriptions, :text
  end
end
