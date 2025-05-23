# frozen_string_literal: true

# == Schema Information
#
# Table name: career_steps
#
#  id                :integer          not null, primary key
#  description       :text             not null
#  locale            :string           not null
#  name              :string           not null
#  notification_kind :string
#  review_needed     :boolean
#  tasks_text        :text             not null
#  created_at        :datetime         not null
#  updated_at        :datetime         not null
#
FactoryBot.define do
  factory :career_step, class: 'Career::Step' do
    name { 'Составить резюме' }
    description { Faker::Lorem.paragraph }
    tasks_text { Faker::Lorem.paragraph }
    locale { 'ru' }
    review_needed { false }
  end
end
