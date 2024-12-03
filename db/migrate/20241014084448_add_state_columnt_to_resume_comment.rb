class AddStateColumntToResumeComment < ActiveRecord::Migration[7.1]
  def change
    add_column :resume_comments, :publishing_state, :string, default: 'published'
  end
end
