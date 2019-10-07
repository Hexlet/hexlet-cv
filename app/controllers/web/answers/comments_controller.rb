# frozen_string_literal: true

class Web::Answers::CommentsController < Web::Answers::ApplicationController
  def edit
    @comment = resource_answer.comments.find params[:id]
    authorize @comment
  end

  def update
    @comment = resource_answer.comments.find params[:id]
    authorize @comment
    if @comment.update(answer_comment_params)
      f(:success)
      redirect_to resume_path(resource_answer.resume)
    else
      render :edit
    end
  end

  def create
    @comment = ResumeAnswerCommentMutator.create(resource_answer, params[:resume_answer_comment], current_user)
    if @comment.persisted?
      f(:success)
      redirect_to resume_path(resource_answer.resume)
    else
      render :new
    end
  end

  def destroy
    comment = resource_answer.comments.find params[:id]
    authorize comment
    comment&.destroy!
    f(:success)

    redirect_to resume_path(resource_answer.resume)
  end

  private

  def answer_comment_params
    params.require(:resume_answer_comment).permit(:content)
  end
end
