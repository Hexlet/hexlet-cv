class RemoveNotConsistentNotifications < ActiveRecord::Migration[7.0]
  def change
    Notification.includes(:resource).find_each do |notification|
      if notification.resource.nil?
        notification.destroy!
      end
    end
  end
end
