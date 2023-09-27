# frozen_string_literal: true

# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rails db:seed command (or created alongside the database with db:setup).
#
# Examples:
#
#   movies = Movie.create([{ name: 'Star Wars' }, { name: 'Lord of the Rings' }])
#   Character.create(name: 'Luke', movie: movies.first)

if Rails.env.staging? && ENV['RENDER']&.to_i&.positive? && ENV['RENDER_LOAD_FIXTURES']&.to_i&.positive?
  require_relative 'seeds/render_load_fixtures'
  render_load_fixtures
end
