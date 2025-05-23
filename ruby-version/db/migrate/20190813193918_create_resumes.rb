# frozen_string_literal: true

class CreateResumes < ActiveRecord::Migration[6.0]
  def change
    create_table :resumes do |t|
      t.string :state
      t.string :name
      t.string :content
      t.references :user, null: false, foreign_key: true

      t.timestamps
    end
  end
end
