# frozen_string_literal: true

# == Schema Information
#
# Table name: countries
#
#  id         :integer          not null, primary key
#  name       :string
#  created_at :datetime         not null
#  updated_at :datetime         not null
#
FactoryBot.define do
  factory :country do
    name { 'MyString' }
  end
end
