# frozen_string_literal: true

class Web::Resumes::CommentsController < Web::Resumes::ApplicationController
  def edit
    @comment = resource_resume.comments.find_by user: current_user, id: params[:id]
  end

  def create
    form = Web::Resumes::CommentForm.new(resume_comment_params)
    @comment = Resume::CommentMutator.create(resource_resume, form.attributes, current_user)
    if @comment.persisted?
      EventSender.serve!(:new_comment_to_resume, @comment)
      f(:success)
      redirect_to resume_path(resource_resume)
    else
      render :new, status: :unprocessable_entity
    end
  end

  def update
    @comment = resource_resume.comments.find_by user: current_user, id: params[:id]
    comment = @comment.becomes(Web::Resumes::CommentForm)
    if comment.update(resume_comment_params)
      f(:success)
      redirect_to resume_path(resource_resume)
    else
      @comment = comment.becomes(Resume::Comment)
      render :edit, status: :unprocessable_entity
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
    params.require(:resume_comment)
  end
end
