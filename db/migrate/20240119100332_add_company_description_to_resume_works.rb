class AddCompanyDescriptionToResumeWorks < ActiveRecord::Migration[7.0]
  def change
    add_column :resume_works, :company_description, :text, limit: 200
  end
end
