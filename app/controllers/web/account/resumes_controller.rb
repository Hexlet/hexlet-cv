# frozen_string_literal: true

class Web::Account::ResumesController < Web::Account::ApplicationController
  def index
    @resumes = current_user.resumes
  end

  def new
    @resume = Resume.new
  end

  def create
    form = Web::Account::ResumeForm.new(resume_params)
    @resume = current_user.resumes.build form.attributes
    if @resume.save
      change_visibility(@resume)
      redirect_to action: :index
    else
      render :new
    end
  end

  def update
    @resume = current_user.resumes.find params[:id]
    resume = @resume.becomes(Web::Account::ResumeForm)
    if resume.update(resume_params)
      change_visibility(@resume)
      f(:success)
      redirect_to action: :index
    else
      @resume = resume.becomes(Resume)
      render :edit
    end
  end

  def edit
    @resume = current_user.resumes.find params[:id]
    @resume_educations = @resume.educations.web
    @resume_works = @resume.works.web
  end

  def destroy; end

  private

  def change_visibility(resume)
    resume.publish! if params[:publish]
    resume.hide! if params[:hide]
  end

  def resume_params
    params.require(:resume)
  end
end
