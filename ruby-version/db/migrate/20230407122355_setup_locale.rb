class SetupLocale < ActiveRecord::Migration[7.0]
  def change
    # rubocop:todo Rails/SkipsModelValidations
    User.update_all(locale: :ru)
    # rubocop:enable Rails/SkipsModelValidations
  end
end
