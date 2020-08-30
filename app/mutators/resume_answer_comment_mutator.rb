# frozen_string_literal: true

module ResumeAnswerCommentMutator
  def self.create(answer, params, current_user)
    comment = answer.comments.build params
    comment.resume = answer.resume
    comment.user = current_user
    comment.answer_user = answer.user
    if comment.save
      if answer.user != comment.user
        answer.user.notifications.create!(kind: :new_answer_comment, resource: comment)
      end
    end

    comment
  end
end
