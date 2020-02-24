class AddCurrentToResumeEducations < ActiveRecord::Migration[6.0]
  def change
    add_column :resume_educations, :current, :boolean
  end
end
