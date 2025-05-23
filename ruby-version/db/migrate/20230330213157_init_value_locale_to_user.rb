# frozen_string_literal: true

class InitValueLocaleToUser < ActiveRecord::Migration[7.0]
  def self.change
    # rubocop:todo Rails/SkipsModelValidations
    User.update_all(locale: :ru)
    # rubocop:enable Rails/SkipsModelValidations
  end
end
