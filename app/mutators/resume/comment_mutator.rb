# frozen_string_literal: true

module Resume::CommentMutator
  def self.create(resume, params, current_user)
    comment = resume.comments.build params
    comment.resume = resume
    comment.user = current_user
    if comment.save
      comment.has_parent? ? create_notification_for_nested_comment(resume, comment) : create_notification_for_comment(resume, comment)
    end

    comment
  end

  def self.create_notification_for_comment(resume, comment)
    resume.user.notifications.create!(kind: :new_comment, resource: comment) unless resume.user == comment.user
  end

  def self.create_notification_for_nested_comment(resume, comment)
    parent_comment = resume.comments.find_by(id: comment.parent_id)
    parent_comment.user.notifications.create!(kind: :new_nested_comment, resource: comment) unless parent_comment.user == comment.user
  end
end
