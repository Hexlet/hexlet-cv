class Web::Answers::LikesController < ApplicationController
  def create
    answer = Resume::Answer.find(params[:answer_id])
    like = answer.likes.build
    like.resume = answer.resume
    like.user = current_user
    like.save!
    flash[:success] = t('flash.web.answers.likes.create.success')

    redirect_to resume_path(answer.resume)
  end

  def destroy
    answer = Resume::Answer.find(params[:answer_id])
    like = answer.likes.find_by user: current_user
    like&.destroy!
    flash[:success] = t('flash.web.answers.likes.destroy.success')

    redirect_to resume_path(answer.resume)
  end
end
