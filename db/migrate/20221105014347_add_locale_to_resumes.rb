class AddLocaleToResumes < ActiveRecord::Migration[6.1]
  def change
    add_column :resumes, :locale, :string
  end
end
