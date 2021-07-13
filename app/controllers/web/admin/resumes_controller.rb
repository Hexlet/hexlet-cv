# frozen_string_literal: true

class Web::Admin::ResumesController < Web::Admin::ApplicationController
  def index
    query = { s: 'created_at desc' }.merge(params.permit![:q] || {})
    @q = Resume.ransack(query)
    @resumes = @q.result(distinct: true).includes(:user).page(params[:page])
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
