# frozen_string_literal: true

FactoryBot.define do
  sequence :first_name do |_n|
    Faker::Name.first_name
  end

  sequence :last_name do |_n|
    Faker::Name.last_name
  end

  sequence :institution do |_n|
    Faker::Educator.university
  end

  sequence :company do |_n|
    Faker::Job.title
  end

  sequence :position do |_n|
    Faker::Job.position
  end

  sequence :begin_date do |_n|
    Time.current
  end

  sequence :description, aliases: %i[skills_description awards_description] do |_n|
    Faker::Lorem.paragraph_by_chars(number: 300)
  end

  sequence :summary do |_n|
    Faker::Lorem.paragraph_by_chars(number: 300)
  end

  sequence :github_url do |n|
    "https://github.com/user#{n}"
  end

  sequence :content do |_n|
    Faker::Lorem.paragraph_by_chars(number: 200)
  end
end
