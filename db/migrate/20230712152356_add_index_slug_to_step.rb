class AddIndexSlugToStep < ActiveRecord::Migration[7.0]
  def change
    add_index :career_steps, :slug, unique: true
  end
end
