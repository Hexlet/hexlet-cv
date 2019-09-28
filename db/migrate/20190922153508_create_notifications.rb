# frozen_string_literal: true

class CreateNotifications < ActiveRecord::Migration[6.0]
  def change
    create_table :notifications do |t|
      t.references :user, null: false, foreign_key: true
      t.references :resource, polymorphic: true, null: false
      t.string :state
      t.string :kind

      t.timestamps
    end
  end
end
