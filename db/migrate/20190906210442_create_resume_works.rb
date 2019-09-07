# frozen_string_literal: true

class CreateResumeWorks < ActiveRecord::Migration[6.0]
  def change
    create_table :resume_works do |t|
      t.references :resume, null: false, foreign_key: true

      t.string :company
      t.string :position
      t.date :start_date
      t.date :end_date
      t.string :description

      t.timestamps
    end
  end
end
