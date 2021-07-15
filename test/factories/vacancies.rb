# frozen_string_literal: true

FactoryBot.define do
  factory :vacancy do
    title { 'MyString' }
    programming_language { 'php' }
    city_name { 'MyString' }
    company_name { 'MyString' }
    site { 'MyString' }
    contact_name { 'MyString' }
    contact_telegram { 'MyString' }
    contact_phone { 'MyString' }
    description { 'MyString' }
  end
end
