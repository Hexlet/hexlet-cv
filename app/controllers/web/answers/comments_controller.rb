# frozen_string_literal: true

class Web::Answers::CommentsController < Web::Answers::ApplicationController
  def edit
    @comment = resource_answer.comments.find_by user: current_user, id: params[:id]
  end

  def update
    @comment = resource_answer.comments.find_by user: current_user, id: params[:id]
    if @comment.update(answer_comment_params)
      f(:success)
      redirect_to resume_path(resource_answer.resume)
    else
      render :edit
    end
  end

  def create
    @comment = resource_answer.comments.build content: params[:resume_answer_comment][:content]
    @comment.resume = resource_answer.resume
    @comment.user = current_user
    @comment.answer_user = resource_answer.user
    if @comment.save
      f(:success)
      redirect_to resume_path(resource_answer.resume)
    else
      render :new
    end
  end

  def destroy
    comment = resource_answer.comments.find_by user: current_user, id: params[:id]
    comment&.destroy!
    f(:success)

    redirect_to resume_path(resource_answer.resume)
  end

  private

  def answer_comment_params
    params.require(:resume_answer_comment).permit(:content)
  end
end
