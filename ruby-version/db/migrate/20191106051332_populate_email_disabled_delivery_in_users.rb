class PopulateEmailDisabledDeliveryInUsers < ActiveRecord::Migration[6.0]
  def change
    User.find_each do |u|
      u.email_disabled_delivery = u.bounced_email || u.marked_as_spam
      u.save!
    end
  end
end
