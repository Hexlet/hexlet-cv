# frozen_string_literal: true

FactoryBot.define do
  factory 'resume/work' do
    company
    position
    begin_date_month
    begin_date_year
    end_date_month
    end_date_year
    description
  end
end
