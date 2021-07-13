# frozen_string_literal: true

FactoryBot.define do
  factory :vacancy do
    creator { nil }
    state { 'MyString' }
    title { 'MyString' }
    language { 'MyString' }
    location { 'MyString' }
    company { 'MyString' }
    site { 'MyString' }
    contact_name { 'MyString' }
    contact_telegram { 'MyString' }
    contact_phone { 'MyString' }
    description { 'MyString' }
  end
end
