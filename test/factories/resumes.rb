# frozen_string_literal: true

# == Schema Information
#
# Table name: resumes
#
#  id                     :integer          not null, primary key
#  about_myself           :text
#  answers_count          :integer          default(0), not null
#  awards_description     :text
#  city                   :string
#  contact                :string
#  contact_email          :string
#  contact_phone          :string
#  contact_telegram       :string
#  english_fluency        :string
#  evaluated_ai           :boolean
#  evaluated_ai_state     :string
#  github_url             :string
#  hexlet_url             :string
#  impressions_count      :integer          default(0)
#  locale                 :string
#  name                   :string           not null
#  projects_description   :text
#  relocation             :string
#  skills_description     :text(250)
#  skills_description_old :text
#  state                  :string
#  summary                :text
#  url                    :string
#  created_at             :datetime         not null
#  updated_at             :datetime         not null
#  user_id                :integer          not null
#
# Indexes
#
#  index_resumes_on_user_id  (user_id)
#
# Foreign Keys
#
#  user_id  (user_id => users.id)
#
FactoryBot.define do
  factory :resume do
    name { Faker::Job.title }
    summary
    skills_description { Array.new(10) { Faker::Hobby.activity }.join("\n") }
    github_url
    contact
    contact_email { 'test@emil.com' }
    projects_description
    about_myself
    english_fluency { :fluent }

    works_attributes do
      [attributes_for('resume/work'), attributes_for('resume/work')]
    end

    educations_attributes do
      [attributes_for('resume/education'), attributes_for('resume/education')]
    end
  end
end
