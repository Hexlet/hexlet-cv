# frozen_string_literal: true

class AddDateFieldsToResumeEducations < ActiveRecord::Migration[6.0]
  def change
    add_column :resume_educations, :begin_date_month, :integer
    add_column :resume_educations, :begin_date_year, :integer
    add_column :resume_educations, :end_date_month, :integer
    add_column :resume_educations, :end_date_year, :integer
  end
end
