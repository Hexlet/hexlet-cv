# frozen_string_literal: true

# == Schema Information
#
# Table name: careers
#
#  id          :integer          not null, primary key
#  description :text             not null
#  locale      :string           not null
#  name        :string           not null
#  slug        :string           not null
#  created_at  :datetime         not null
#  updated_at  :datetime         not null
#
# Indexes
#
#  index_careers_on_slug  (slug) UNIQUE
#
FactoryBot.define do
  factory :career do
    name { 'Аналитик' }
    description { Faker::Lorem.paragraph }
    slug { Faker::Lorem.word }
    locale { 'ru' }

    items_attributes do
      []
    end
  end
end
