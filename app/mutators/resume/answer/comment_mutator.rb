# frozen_string_literal: true

module Resume::Answer::CommentMutator
  def self.create(answer, params, current_user)
    comment = answer.comments.build params
    comment.resume = answer.resume
    comment.user = current_user
    comment.answer_user = answer.user
    if comment.save
      comment.has_parent? ? create_notification_for_nested_comment(answer, comment) : create_notification_for_comment(answer, comment)
    end

    comment
  end

  def self.create_notification_for_comment(answer, comment)
    answer.user.notifications.create!(kind: :new_answer_comment, resource: comment) unless answer.user == comment.user
  end

  def self.create_notification_for_nested_comment(answer, comment)
    parent_comment = answer.comments.find_by(id: comment.parent_id)
    parent_comment.user.notifications.create!(kind: :new_answer_nested_comment, resource: comment) unless parent_comment.user == comment.user
  end
end
