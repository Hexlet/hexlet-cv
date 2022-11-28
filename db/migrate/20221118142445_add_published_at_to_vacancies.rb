class AddPublishedAtToVacancies < ActiveRecord::Migration[6.1]
  def change
    add_column :vacancies, :published_at, :datetime
  end
end
