# frozen_string_literal: true

FactoryBot.define do
  factory :user do
    first_name
    last_name
    about
    email { Faker::Internet.email }
    password { Faker::Number.number }
  end
end
