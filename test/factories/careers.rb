# frozen_string_literal: true

FactoryBot.define do
  factory :career do
    name { 'Аналитик' }
    description { Faker::Lorem.paragraph }
    slug { 'analitic' }
    locale { 'ru' }

    items_attributes do
      []
    end
  end
end
