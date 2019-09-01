# frozen_string_literal: true

class Web::Account::ResumesController < ApplicationController
  def index
    @resumes = current_user.resumes
  end

  def new
    template = File.read(Rails.root.join('config', 'resume-template.yml'))
    @resume = Resume.new content: template
  end

  def create
    resume_params = params.require(:resume).permit(:name, :content)
    @resume = current_user.resumes.build resume_params
    if @resume.save
      redirect_to action: :index
    else
      render :new
    end
  end

  def update
    resume_params = params.require(:resume).permit(:name, :content)
    @resume = current_user.resumes.find params[:id]
    if @resume.update(resume_params)
      redirect_to action: :index
    else
      render :edit
    end
  end

  def edit
    @resume = current_user.resumes.find params[:id]
  end

  def destroy; end
end
