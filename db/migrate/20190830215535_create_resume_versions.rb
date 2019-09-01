# frozen_string_literal: true

class CreateResumeVersions < ActiveRecord::Migration[6.0]
  def change
    create_table :resume_versions do |t|
      t.references :resume, null: false, foreign_key: true
      t.references :user, null: false, foreign_key: true
      t.text :content

      t.timestamps
    end
  end
end
