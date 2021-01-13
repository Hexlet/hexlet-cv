# frozen_string_literal: true

module Resume::Answer::LikeMutator
  def self.create(resource_answer, current_user)
    like = resource_answer.likes.build
    like.resume = resource_answer.resume
    like.user = current_user
    resource_answer.user.notifications.create!(kind: :new_answer_like, resource: like) if like.save

    like
  end
end
