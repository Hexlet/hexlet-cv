# frozen_string_literal: true

# == Schema Information
#
# Table name: resume_answer_comments
#
#  id             :integer          not null, primary key
#  content        :string
#  created_at     :datetime         not null
#  updated_at     :datetime         not null
#  answer_id      :integer          not null
#  answer_user_id :integer          not null
#  resume_id      :integer          not null
#  user_id        :integer          not null
#
# Indexes
#
#  index_resume_answer_comments_on_answer_id       (answer_id)
#  index_resume_answer_comments_on_answer_user_id  (answer_user_id)
#  index_resume_answer_comments_on_resume_id       (resume_id)
#  index_resume_answer_comments_on_user_id         (user_id)
#
# Foreign Keys
#
#  answer_id       (answer_id => resume_answers.id)
#  answer_user_id  (answer_user_id => users.id)
#  resume_id       (resume_id => resumes.id)
#  user_id         (user_id => users.id)
#
require 'test_helper'

class Resume::Answer::CommentTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end
end
