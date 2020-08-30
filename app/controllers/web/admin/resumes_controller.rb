# frozen_string_literal: true

class Web::Admin::ResumesController < Web::Admin::ApplicationController
  def index
    @q = Resume.web_admin.ransack(params[:q])
    @resumes = @q.result(distinct: true).page(params[:page])
  end

  def update
    @resume = Resume.find params[:id]
    resume = @resume.becomes(Web::Admin::ResumeForm)
    if resume.update(resume_params)
      f(:success)
      redirect_to action: :index
    else
      @resume = resume.becomes(Resume)
      render :edit
    end
  end

  def edit
    @resume = Resume.find params[:id]
    @resume_educations = @resume.educations.web
    @resume_works = @resume.works.web
  end

  private

  def resume_params
    params.require(:resume)
  end
end
