# frozen_string_literal: true

FactoryBot.define do
  sequence :first_name do |_n|
    Faker::Name.first_name
  end

  sequence :last_name do |_n|
    Faker::Name.last_name
  end
end
