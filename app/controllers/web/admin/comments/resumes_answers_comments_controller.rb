# frozen_string_literal: true

class Web::Admin::Comments::ResumesAnswersCommentsController < Web::Admin::Comments::ApplicationController
  before_action :set_answers_comment, only: %i[edit update archive restore]

  def edit; end

  def update
    if @comment.update(comment_params)
      f(:success)
      redirect_to admin_resumes_answers_path
    else
      f(:error)
      render :edit, status: :unprocessable_entity
    end
  end

  def archive
    return unless @comment.may_archive?

    @comment.archive!
    f(:success)
    redirect_to admin_resumes_answers_path
  end

  def restore
    return unless @comment.may_restore?

    @comment.restore!
    f(:success)
    redirect_to admin_resumes_answers_path
  end

  private

  def set_answers_comment
    @comment = Resume::Answer::Comment.find(params[:id])
  end

  def comment_params
    params.require(:resume_answer_comment).permit(:content)
  end
end
