# frozen_string_literal: true

class RemoveContentFromResume < ActiveRecord::Migration[6.0]
  def change
    remove_column :resumes, :content, :string
  end
end
