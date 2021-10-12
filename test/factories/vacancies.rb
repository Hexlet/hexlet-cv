# frozen_string_literal: true

FactoryBot.define do
  factory :vacancy do
    title { 'MyString' }
    salary_from { '3000' }
    programming_language { 'php' }
    city_name { 'MyString' }
    company_name { 'MyString' }
    site { 'https://ru.hexlet.io' }
    contact_name { 'MyString' }
    contact_telegram { 'MyString' }
    contact_phone { 'MyString' }
    responsibilities_description { 'MyString' }
  end
end
