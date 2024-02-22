# frozen_string_literal: true

# == Schema Information
#
# Table name: resume_works
#
#  id                  :integer          not null, primary key
#  begin_date          :date
#  company             :string
#  company_description :text(200)
#  current             :boolean
#  description         :string
#  end_date            :date
#  position            :string
#  created_at          :datetime         not null
#  updated_at          :datetime         not null
#  resume_id           :integer          not null
#
# Indexes
#
#  index_resume_works_on_resume_id  (resume_id)
#
# Foreign Keys
#
#  resume_id  (resume_id => resumes.id)
#
require 'test_helper'

class Resume::WorkTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end
end
