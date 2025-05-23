class AddCurrentToResumeWorks < ActiveRecord::Migration[6.0]
  def change
    add_column :resume_works, :current, :boolean
  end
end
