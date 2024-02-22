# frozen_string_literal: true

# == Schema Information
#
# Table name: resumes
#
#  id                   :integer          not null, primary key
#  about_myself         :text
#  answers_count        :integer          default(0), not null
#  awards_description   :text
#  city                 :string
#  contact              :string
#  contact_email        :string
#  contact_phone        :string
#  contact_telegram     :string
#  english_fluency      :string
#  evaluated_ai         :boolean
#  evaluated_ai_state   :string
#  github_url           :string
#  hexlet_url           :string
#  impressions_count    :integer          default(0)
#  locale               :string
#  name                 :string           not null
#  projects_description :text
#  relocation           :string
#  skills_description   :text
#  state                :string
#  summary              :text
#  url                  :string
#  created_at           :datetime         not null
#  updated_at           :datetime         not null
#  user_id              :integer          not null
#
# Indexes
#
#  index_resumes_on_user_id  (user_id)
#
# Foreign Keys
#
#  user_id  (user_id => users.id)
#
require 'test_helper'

class ResumeTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end
end
