class PopulateRoleInUsers < ActiveRecord::Migration[6.0]
  def change
    User.where(role: nil).update_all(role: :user) # rubocop:disable Rails/SkipsModelValidations
  end
end
