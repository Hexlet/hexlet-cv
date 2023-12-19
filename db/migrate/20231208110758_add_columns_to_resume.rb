# frozen_string_literal: true
class AddColumnsToResume < ActiveRecord::Migration[7.0]
  def change
    add_column :resumes, :about_my_self, :text
    add_column :resumes, :project_descriptions, :text
  end
end
