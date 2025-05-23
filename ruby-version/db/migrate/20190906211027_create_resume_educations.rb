# frozen_string_literal: true

class CreateResumeEducations < ActiveRecord::Migration[6.0]
  def change
    create_table :resume_educations do |t|
      t.references :resume, null: false, foreign_key: true

      t.string :institution
      t.string :degree
      t.string :faculty
      t.date :start_date
      t.date :end_date

      t.timestamps
    end
  end
end
