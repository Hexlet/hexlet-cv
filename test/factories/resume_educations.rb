# frozen_string_literal: true

FactoryBot.define do
  factory 'resume/education' do
    institution
    begin_date_month
    begin_date_year
    end_date_month
    end_date_year
  end
end
