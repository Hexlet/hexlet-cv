class CreateLeads < ActiveRecord::Migration[7.0]
  def change
    create_table :leads do |t|
      t.string :user_name, null: false
      t.string :phone_number, null: false
      t.string :email, null: false

      t.timestamps
    end
  end
end
