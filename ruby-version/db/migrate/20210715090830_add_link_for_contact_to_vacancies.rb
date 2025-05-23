class AddLinkForContactToVacancies < ActiveRecord::Migration[6.1]
  def change
    add_column :vacancies, :link_for_contact, :string
  end
end
