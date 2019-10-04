class AddResumeEducationsDescription < ActiveRecord::Migration[6.0]
  def change
    add_column :resume_educations, :description, :string
  end
end
