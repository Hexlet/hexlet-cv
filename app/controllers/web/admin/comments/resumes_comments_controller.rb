# frozen_string_literal: true

class Web::Admin::Comments::ResumesCommentsController < Web::Admin::Comments::ApplicationController
  before_action :set_comment, only: %i[edit update archive restore]
  def index
    @search = Resume::Comment.web.includes(:user).ransack(params[:q])
    @resumes_comments = @search.result(sort: 'created_at desc').page(params[:page])
  end

  def archive
    return unless @comment.may_archive?

    @comment.archive!
    f(:success)
    redirect_to admin_resumes_comments_path
  end

  def restore
    return unless @comment.may_restore?

    @comment.restore!
    f(:success)
    redirect_to admin_resumes_comments_path
  end

  def archived
    @search = Resume::Comment.web.includes(:user).where(publishing_state: 'archived').ransack(params[:q])
    @resumes_comments = @search.result(sort: 'created_at desc').page(params[:page])
  end

  def edit; end

  def update
    if @comment.update(comment_params)
      f(:success)
      redirect_to admin_resumes_comments_path
    else
      f(:error)
      render :edit, status: :unprocessable_entity
    end
  end

  private

  def set_comment
    @comment = Resume::Comment.find(params[:id])
  end

  def comment_params
    params.require(:resume_comment).permit(:content)
  end
end
