# frozen_string_literal: true

class Web::Resumes::CommentsController < Web::Resumes::ApplicationController
  before_action :authenticate_user!

  def create
    comment = resource_resume.comments.build content: params[:resume_comment][:content]
    comment.resume = resource_resume
    comment.user = current_user
    comment.save!
    f(:success)

    redirect_to resume_path(resource_resume)
  end

  def destroy
    comment = resource_resume.comments.find_by user: current_user, id: params[:id]
    comment&.destroy!
    f(:success)

    redirect_to resume_path(resource_resume)
  end
end
