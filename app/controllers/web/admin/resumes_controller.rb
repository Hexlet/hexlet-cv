# frozen_string_literal: true

class Web::Admin::ResumesController < Web::Admin::ApplicationController
  def index
    @q = Resume.web_admin.ransack(params[:q])
    @resumes = @q.result(distinct: true).page(params[:page])
  end

  def update
    @resume = Resume.find params[:id]
    if @resume.update(resume_params)
      f(:success)
      redirect_to action: :index
    else
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
    attrs = %i[state_event name hexlet_url github_url summary skills_description awards_description english_fluency]
    nested_attrs = {
      educations_attributes: %i[description begin_date end_date current _destroy id],
      works_attributes: %i[company position description begin_date end_date current _destroy id]
    }
    params.require(:resume).permit(*attrs, **nested_attrs)
  end
end
