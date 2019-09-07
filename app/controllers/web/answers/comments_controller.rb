# frozen_string_literal: true

class Web::Answers::CommentsController < Web::Answers::ApplicationController
  # def new
  # end

  # def edit
  # end

  def create
    comment = resource_answer.comments.build content: params[:resume_answer_comment][:content]
    comment.resume = resource_answer.resume
    comment.user = current_user
    comment.answer_user = resource_answer.user
    comment.save!
    flash[:success] = t('flash.web.answers.comments.create.success')

    redirect_to resume_path(resource_answer.resume)
  end

  def destroy
    comment = resource_answer.comments.find_by user: current_user
    comment&.destroy!
    flash[:success] = t('flash.web.answers.comments.destroy.success')

    redirect_to resume_path(resource_answer.resume)
  end
end
