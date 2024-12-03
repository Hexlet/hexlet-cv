# frozen_string_literal: true

# == Schema Information
#
# Table name: resume_answers
#
#  id               :integer          not null, primary key
#  applying_state   :string
#  content          :text
#  likes_count      :integer
#  publishing_state :string           default("published")
#  created_at       :datetime         not null
#  updated_at       :datetime         not null
#  resume_id        :integer          not null
#  user_id          :integer          not null
#
# Indexes
#
#  index_resume_answers_on_resume_id              (resume_id)
#  index_resume_answers_on_user_id                (user_id)
#  index_resume_answers_on_user_id_and_resume_id  (user_id,resume_id) UNIQUE
#
# Foreign Keys
#
#  resume_id  (resume_id => resumes.id)
#  user_id    (user_id => users.id)
#
FactoryBot.define do
  factory 'resume/answer' do
    content
  end
end
