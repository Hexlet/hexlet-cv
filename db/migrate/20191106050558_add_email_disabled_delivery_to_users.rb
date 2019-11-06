class AddEmailDisabledDeliveryToUsers < ActiveRecord::Migration[6.0]
  def change
    add_column :users, :email_disabled_delivery, :boolean
  end
end
