class AddNotNullConstrainSlugStep < ActiveRecord::Migration[7.0]
  def change
    change_column_null :career_steps, :slug, false
  end
end
