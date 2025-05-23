class RemoveUserAnshlyapnikov < ActiveRecord::Migration[7.0]
  def change
    user = User.find_by(email: 'anshlyapnikov@icloud.com')

    return unless user

    user.destroy!
  end
end
