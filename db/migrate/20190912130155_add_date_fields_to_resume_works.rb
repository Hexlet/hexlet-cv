# frozen_string_literal: true

class AddDateFieldsToResumeWorks < ActiveRecord::Migration[6.0]
  def change
    add_column :resume_works, :begin_date_month, :integer
    add_column :resume_works, :begin_date_year, :integer
    add_column :resume_works, :end_date_month, :integer
    add_column :resume_works, :end_date_year, :integer
  end
end
