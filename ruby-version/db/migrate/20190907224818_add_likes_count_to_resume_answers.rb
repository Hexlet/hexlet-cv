class AddLikesCountToResumeAnswers < ActiveRecord::Migration[6.0]
  def change
    add_column :resume_answers, :likes_count, :integer
  end
end
