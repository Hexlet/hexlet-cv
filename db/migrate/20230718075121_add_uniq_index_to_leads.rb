class AddUniqIndexToLeads < ActiveRecord::Migration[7.0]
  def change
    add_index :leads, :email, unique: true
  end
end
