# frozen_string_literal: true

class Web::Account::ResumesController < ApplicationController
  def index
    @resumes = current_user.resumes
  end

  def new
    template = File.read(Rails.root.join('config', 'resume-template.yml'))
    @resume = Resume.new
    @resume.versions.build content: template
  end

  def create
    resume_params = params.require(:resume).permit(:name, versions_attributes: [:content])
    @resume = current_user.resumes.build resume_params
    @resume.versions.build if @resume.versions.empty?
    if @resume.save
      redirect_to action: :index
    else
      render :new
    end
  end

  def destroy; end
end
