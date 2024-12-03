class AddStateColumntToResumeAnswerComment < ActiveRecord::Migration[7.1]
  def change
    add_column :resume_answer_comments, :publishing_state, :string, default: 'published'
  end
end
