# frozen_string_literal: true
class AddColumnsToResume < ActiveRecord::Migration[7.0]
  def change
    add_column :resumes, :about_me, :text
    add_column :resumes, :projects, :text
  end
end
