# frozen_string_literal: true

class AddUniqInidexToResumeAnswerLike < ActiveRecord::Migration[6.1]
  def change
    add_index(:resume_answer_likes, %i[answer_id user_id], unique: true)
  end
end
