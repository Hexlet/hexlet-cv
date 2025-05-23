class AddColumnsProjectsDescriptionAndAboutMyselfFromResume < ActiveRecord::Migration[7.0]
  def change
    add_column :resumes, :projects_description, :text
    add_column :resumes, :about_myself, :text
  end
end
