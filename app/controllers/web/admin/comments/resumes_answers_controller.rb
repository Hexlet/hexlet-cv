# frozen_string_literal: true

class Web::Admin::Comments::ResumesAnswersController < Web::Admin::Comments::ApplicationController
  before_action :set_answer, only: %i[edit update archive restore]

  def index
    @search = Resume::Answer.web.includes(comments: :user).ransack(params[:q])
    @resumes_answers = @search.result(sort: 'created_at desc').page(params[:page])
  end

  def edit; end

  def update
    if @answer.update(answer_params)
      f(:success)
      redirect_to admin_resumes_answers_path
    else
      f(:error)
      render :edit, status: :unprocessable_entity
    end
  end

  def archive
    return unless @answer.may_archive?

    @answer.archive!
    f(:success)
    redirect_to admin_resumes_answers_path
  end

  def restore
    return unless @answer.may_restore?

    @answer.restore!
    f(:success)
    redirect_to admin_resumes_answers_path
  end

  private

  def set_answer
    @answer = Resume::Answer.find(params[:id])
  end

  def answer_params
    params.require(:resume_answer).permit(:content)
  end
end
