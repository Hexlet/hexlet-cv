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
require 'test_helper'

class Resume::EducationTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end
end
