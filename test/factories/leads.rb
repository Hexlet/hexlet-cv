# frozen_string_literal: true

FactoryBot.define do
  factory :lead do
    user_name { Faker::Name.name }
    phone_numper { Faker::PhoneNumber.cell_phone }
    email { Faker::Internet.email }
  end
end
