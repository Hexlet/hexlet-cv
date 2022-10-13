class AddAncestryToResumeComments < ActiveRecord::Migration[6.1]
  def change
    add_column :resume_comments, :ancestry, :string
    add_index :resume_comments, :ancestry
  end
end
