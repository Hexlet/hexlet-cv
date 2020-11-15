# frozen_string_literal: true

module Resume::CommentMutator
  def self.create(resume, params, current_user)
    comment = resume.comments.build params
    comment.resume = resume
    comment.user = current_user
    if comment.save
      resume.user.notifications.create!(kind: :new_comment, resource: comment)
    end

    comment
  end
end
