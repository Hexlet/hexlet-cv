# frozen_string_literal: true

class MakeResumeFieldsNullable < ActiveRecord::Migration[7.0]
  def change
    change_column_null :resumes, :github_url, true
    change_column_null :resumes, :summary, true
    change_column_null :resumes, :skills_description, true
    change_column_null :resumes, :contact_email, true
  end
end
