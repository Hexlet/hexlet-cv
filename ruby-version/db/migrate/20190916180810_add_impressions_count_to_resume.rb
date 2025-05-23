class AddImpressionsCountToResume < ActiveRecord::Migration[6.0]
  def change
    add_column :resumes, :impressions_count, :integer, :default => 0
  end
end
