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
      redirect_to admin_resumes_answers_path
    else
      render :edit
    end
  end

  def archive
    @answer.archive! if @answer.may_archive?
    redirect_to admin_resumes_answers_path, notice: t('.success')
  end

  def restore
    @answer.restore! if @answer.may_restore?
    redirect_to admin_resumes_answers_path, notice: t('.success')
  end

  private

  def set_answer
    @answer = Resume::Answer.find(params[:id])
  end

  def answer_params
    params.require(:resume_answer)
  end
end
