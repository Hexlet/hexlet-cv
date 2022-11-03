# frozen_string_literal: true

class Web::Admin::ResumesController < Web::Admin::ApplicationController
  def index
    query = { s: 'created_at desc' }.merge(params.permit![:q] || {})
    @q = Resume.ransack(query)
    @resumes = @q.result(distinct: true).includes(:user).page(params[:page])
  end

  def edit
    @resume = Resume.find params[:id]
    @resume_educations = @resume.educations.web
    @resume_works = @resume.works.web
  end

  def update
    resume = Resume.find params[:id]
    @resume = resume.becomes(Web::Admin::ResumeForm)
    if @resume.update(params[:resume])
      f(:success)
      redirect_to action: :index
    else
      render :edit
    end
  end
end
