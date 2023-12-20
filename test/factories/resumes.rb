# frozen_string_literal: true

FactoryBot.define do
  factory :resume do
    name { Faker::Job.title }
    summary
    skills_description
    github_url
    contact
    contact_email { 'test@emil.com' }
    project_descriptions
    about_my_self
    english_fluency { :fluent }

    works_attributes do
      [attributes_for('resume/work'), attributes_for('resume/work')]
    end

    educations_attributes do
      [attributes_for('resume/education'), attributes_for('resume/education')]
    end
  end
end
