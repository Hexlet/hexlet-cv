class AddResumeAnswerLikesCountToUsers < ActiveRecord::Migration[6.0]
  def change
    add_column :users, :resume_answer_likes_count, :integer, null: false, default: 0
  end
end
