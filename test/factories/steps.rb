# frozen_string_literal: true

FactoryBot.define do
  factory :step do
    name { 'Составить резюме' }
    description { Faker::Lorem.paragraph }
    tasks_text { Faker::Lorem.paragraph }
    locale { 'ru' }
    review_needed { false }
  end
end
