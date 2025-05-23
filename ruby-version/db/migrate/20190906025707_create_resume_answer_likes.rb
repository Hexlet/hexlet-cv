class CreateResumeAnswerLikes < ActiveRecord::Migration[6.0]
  def change
    create_table :resume_answer_likes do |t|
      t.references :resume, null: false, foreign_key: true
      t.references :resume_answer, null: false, foreign_key: true
      t.references :user, null: false, foreign_key: true

      t.timestamps
    end
  end
end
