# frozen_string_literal: true

class Web::Resumes::AnswersController < ApplicationController
  before_action :authenticate_user!

  def create
    @resume = Resume.find params[:resume_id]
    @answer = @resume.answers.build resume_answer_params
    @answer.user = current_user
    if @answer.save
      redirect_to resume_path(@resume)
    else
      render action: 'new'
    end
  end

  def resume_answer_params
    params.require(:resume_answer).permit(:content)
  end
end
