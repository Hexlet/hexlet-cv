# frozen_string_literal: true

class Web::Resumes::CommentsController < Web::Resumes::ApplicationController
  def edit
    @comment = resource_resume.comments.find_by user: current_user, id: params[:id]
  end

  def update
    @comment = resource_resume.comments.find_by user: current_user, id: params[:id]
    if @comment.update(resume_comment_params)
      f(:success)
      redirect_to resume_path(resource_resume)
    else
      render :edit
    end
  end

  def create
    @comment = resource_resume.comments.build content: params[:resume_comment][:content]
    @comment.resume = resource_resume
    @comment.user = current_user
    if @comment.save
      f(:success)
      redirect_to resume_path(resource_resume)
    else
      render :new
    end
  end

  def destroy
    comment = resource_resume.comments.find_by user: current_user, id: params[:id]
    comment&.destroy!
    f(:success)

    redirect_to resume_path(resource_resume)
  end

  private

  def resume_comment_params
    params.require(:resume_comment).permit(:content)
  end
end
