# frozen_string_literal: true

class CreateResumeComments < ActiveRecord::Migration[6.0]
  def change
    create_table :resume_comments do |t|
      t.references :resume
      t.references :user
      t.string :content

      t.timestamps
    end
  end
end
