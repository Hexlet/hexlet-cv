# frozen_string_literal: true

# == Schema Information
#
# Table name: resume_educations
#
#  id          :integer          not null, primary key
#  begin_date  :date
#  current     :boolean
#  description :string
#  end_date    :date
#  faculty     :string
#  institution :string
#  created_at  :datetime         not null
#  updated_at  :datetime         not null
#  resume_id   :integer          not null
#
# Indexes
#
#  index_resume_educations_on_resume_id  (resume_id)
#
# Foreign Keys
#
#  resume_id  (resume_id => resumes.id)
#
FactoryBot.define do
  factory 'resume/education' do
    description
    begin_date
  end
end
