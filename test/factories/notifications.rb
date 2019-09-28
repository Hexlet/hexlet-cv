# frozen_string_literal: true

FactoryBot.define do
  factory :notification do
    user { nil }
    resourceable { nil }
    state { 'MyString' }
    type { '' }
  end
end
