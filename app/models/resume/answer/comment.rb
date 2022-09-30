# frozen_string_literal: true

class Resume::Answer::Comment < ApplicationRecord
  validates :content, presence: true, length: { minimum: 10, maximum: 400 }

  belongs_to :resume
  belongs_to :answer, inverse_of: :comments
  belongs_to :user
  belongs_to :answer_user, class_name: 'User'
  has_many :notifications, as: :resource, dependent: :destroy

  include Resume::Answer::CommentRepository

  def to_s
    content
  end

  def send_new_comment_email
    return nil unless resume.user.can_send_email? && resume.user.resume_mail_enabled

    AnswerCommentMailer.with(comment: self).new_comment_email.deliver_later
  end
end
