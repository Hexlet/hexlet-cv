# frozen_string_literal: true

class UpdateResumeWorksAndEducationsFields < ActiveRecord::Migration[6.0]
  def change
    change_table :resume_educations do |t|
      t.remove :degree
      t.rename :start_date, :begin_date
    end

    rename_column :resume_works, :start_date, :begin_date
  end
end
