# frozen_string_literal: true

class Web::Resumes::AnswersController < ApplicationController
  before_action :authenticate_user!

  def create
    @resume = Resume.find params[:resume_id]
    @answer = @resume.answers.build resume_answer_params
    @answer.user = current_user
    if @answer.save
      flash[:success] = t('flash.web.resumes.create.success')
      redirect_to resume_path(@resume)
    else
      render action: 'new'
    end
  end

  def destroy
    resume = Resume.find params[:resume_id]
    # TODO: add pundit, switch to resume, check current user
    answer = current_user.resume_answers.find(params[:id])
    answer.destroy

    # TODO: make an abstraction
    flash[:success] = t('flash.web.resumes.answers.delete.success')
    redirect_to resume_path(resume)
  end

  def resume_answer_params
    params.require(:resume_answer).permit(:content)
  end
end
