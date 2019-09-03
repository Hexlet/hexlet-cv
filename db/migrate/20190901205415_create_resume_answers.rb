# frozen_string_literal: true

class CreateResumeAnswers < ActiveRecord::Migration[6.0]
  def change
    create_table :resume_answers do |t|
      t.references :resume, null: false, foreign_key: true
      t.references :user, null: false, foreign_key: true
      t.text :content

      t.timestamps
    end
  end
end
