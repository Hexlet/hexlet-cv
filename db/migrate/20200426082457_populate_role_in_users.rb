class PopulateRoleInUsers < ActiveRecord::Migration[6.0]
  def change
    User.where(role: nil).update_all(role: 0) # rubocop:disable Rails/SkipsModelValidations
  end
end
