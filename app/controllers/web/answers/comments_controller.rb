# frozen_string_literal: true

class Web::Answers::CommentsController < Web::Answers::ApplicationController
  def edit
    @comment = resource_answer.comments.find params[:id]
    authorize @comment
  end

  def update
    @comment = resource_answer.comments.find params[:id]
    authorize @comment
    comment = @comment.becomes(Web::Resumes::Answers::CommentForm)
    if comment.update(answer_comment_params)
      f(:success)
      redirect_to resume_path(resource_answer.resume)
    else
      @comment = comment.becomes(Resume::Answer::Comment)
      render :edit
    end
  end

  def create
    form = Web::Resumes::Answers::CommentForm.new(answer_comment_params)
    @comment = Resume::Answer::CommentMutator.create(resource_answer, form.attributes, current_user)
    if @comment.persisted?
      @comment.send_new_comment_email
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
    params.require(:resume_answer_comment)
  end
end
