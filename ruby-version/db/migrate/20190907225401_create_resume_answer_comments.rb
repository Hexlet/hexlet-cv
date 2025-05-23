class CreateResumeAnswerComments < ActiveRecord::Migration[6.0]
  def change
    create_table :resume_answer_comments do |t|
      t.references :resume, null: false, foreign_key: true
      t.references :resume_answer, null: false, foreign_key: true
      t.references :user, null: false, foreign_key: true
      t.references :answer_user, null: false, foreign_key: { to_table: :users }
      t.string :content

      t.timestamps
    end
  end
end
