# frozen_string_literal: true

FactoryBot.define do
  factory :event do
    user { nil }
    locale { 'ru' }
  end
end
