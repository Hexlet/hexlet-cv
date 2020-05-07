class PopulateStateInUsers < ActiveRecord::Migration[6.0]
  def change
    User.where(state: nil).update_all(state: :permitted) # rubocop:disable Rails/SkipsModelValidations
  end
end
