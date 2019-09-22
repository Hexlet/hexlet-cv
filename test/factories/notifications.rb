FactoryBot.define do
  factory :notification do
    user { nil }
    resourceable { nil }
    state { "MyString" }
    type { "" }
  end
end
