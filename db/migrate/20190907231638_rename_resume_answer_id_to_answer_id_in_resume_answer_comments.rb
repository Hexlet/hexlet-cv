class RenameResumeAnswerIdToAnswerIdInResumeAnswerComments < ActiveRecord::Migration[6.0]
  def change
    rename_column :resume_answer_comments, :resume_answer_id, :answer_id
  end
end
