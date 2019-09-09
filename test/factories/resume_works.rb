# frozen_string_literal: true

FactoryBot.define do
  factory 'resume/work' do
    company
    position
    begin_date
    description
  end
end
