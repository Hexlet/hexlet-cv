# frozen_string_literal: true

FactoryBot.define do
  factory :user do
    first_name
    last_name
    about
    email { Faker::Internet.email }
    password { Faker::Number.number }
    strides { Study::GOALS.keys.sample(rand(Study::GOALS.length)) }
  end
end
