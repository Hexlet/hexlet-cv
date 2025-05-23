# frozen_string_literal: true

module Resume::Answer::CommentMutator
  def self.create(answer, params, current_user)
    comment = answer.comments.build params
    comment.resume = answer.resume
    comment.user = current_user
    comment.answer_user = answer.user
    if comment.save && (answer.user != comment.user)
      answer.user.notifications.create!(kind: :new_answer_comment, resource: comment)
    end

    comment
  end
end
