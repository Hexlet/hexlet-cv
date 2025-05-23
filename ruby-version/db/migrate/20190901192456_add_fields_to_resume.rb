# frozen_string_literal: true

class AddFieldsToResume < ActiveRecord::Migration[6.0]
  def change
    add_column :resumes, :url, :string
    add_column :resumes, :summary, :text
    add_column :resumes, :skills_description, :text
    add_column :resumes, :github_url, :string
    add_column :resumes, :awards_description, :text
    add_column :resumes, :english_fluency, :string
  end
end
