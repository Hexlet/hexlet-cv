class UpdateResumeSkillDescription < ActiveRecord::Migration[7.0]
  def change
    rename_column :resumes, :skills_description, :skills_description_old
    add_column :resumes, :skills_description, :text
  end
end
