# frozen_string_literal: true

# == Schema Information
#
# Table name: resume_comments
#
#  id               :integer          not null, primary key
#  content          :string
#  publishing_state :string           default("published")
#  created_at       :datetime         not null
#  updated_at       :datetime         not null
#  resume_id        :integer
#  user_id          :integer
#
# Indexes
#
#  index_resume_comments_on_resume_id  (resume_id)
#  index_resume_comments_on_user_id    (user_id)
#
FactoryBot.define do
  factory 'resume/comment' do
    content
  end
end
