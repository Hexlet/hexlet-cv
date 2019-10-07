# frozen_string_literal: true

class Web::Answers::LikesController < Web::Answers::ApplicationController
  def create
    like = resource_answer.likes.build
    like.resume = resource_answer.resume
    like.user = current_user
    if like.save
      resource_answer.user.notifications.create!(kind: :new_answer_like, resource: like)
      f(:success)
    else
      f(:error, errors: like.errors.full_messages.to_sentence)
    end

    redirect_to resume_path(resource_answer.resume)
  end

  def destroy
    like = resource_answer.likes.find_by user: current_user
    like&.destroy!
    f(:success)

    redirect_to resume_path(resource_answer.resume)
  end
end
