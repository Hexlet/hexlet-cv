# frozen_string_literal: true

class Web::Resumes::CommentsController < ApplicationController
  before_action :authenticate_user!

  def create
    @resume = Resume.find params[:resume_id]
    @comment = @resume.comments.build content: params[:resume_comment][:content]
    @comment.user = current_user
    @comment.save!
    flash[:success] = t('flash.web.resumes.create.success')

    redirect_to resume_path(@resume)
  end

  def destroy
    resume = Resume.find(params[:resume_id])
    comment = resume.comments.find_by user: current_user, id: params[:id]
    comment&.destroy!
    flash[:success] = t('flash.web.resume.comments.destroy.success')

    redirect_to resume_path(resume)
  end

  def resume_comment_params
    params.require(:resume_comment).permit(:content)
  end
end
