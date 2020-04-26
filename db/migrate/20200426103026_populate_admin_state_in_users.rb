class PopulateAdminStateInUsers < ActiveRecord::Migration[6.0]
  def change
    User.where(admin_state: nil).update_all(admin_state: :permitted) # rubocop:disable Rails/SkipsModelValidations
  end
end
