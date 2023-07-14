class RenameColumnSlugToCareerStep < ActiveRecord::Migration[7.0]
  def change
    rename_column :career_steps, :slug, :notification_kind
  end
end
