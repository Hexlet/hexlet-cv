class AddUniqIndexForUserResumeToResumeAnswers < ActiveRecord::Migration[6.0]
  def change
    add_index :resume_answers, [:user_id, :resume_id], unique: true
  end
end
