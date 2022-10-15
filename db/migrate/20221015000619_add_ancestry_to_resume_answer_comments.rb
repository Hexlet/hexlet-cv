class AddAncestryToResumeAnswerComments < ActiveRecord::Migration[6.1]
  def change
    add_column :resume_answer_comments, :ancestry, :string
    add_index :resume_answer_comments, :ancestry
  end
end
