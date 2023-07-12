class AddSlugToCareerStep < ActiveRecord::Migration[7.0]
  def change
    add_column :career_steps, :slug, :string
  end
end
