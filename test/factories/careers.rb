# frozen_string_literal: true

FactoryBot.define do
  factory :career do
    name { 'Инженер по тестированию' }
    description { Faker::Lorem.paragraph }
    slug { 'test-engineer' }
    locale { 'ru' }

    items_attributes do
      []
    end
  end
end
