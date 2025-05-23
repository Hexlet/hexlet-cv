class AddColumnCityToResumes < ActiveRecord::Migration[6.1]
  def change
    add_column :resumes, :city, :string
  end
end
