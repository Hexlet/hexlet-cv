# frozen_string_literal: true

class Web::Account::ResumesController < Web::Account::ApplicationController
  def index
    @resumes = current_user.resumes
  end

  def new
    @resume = Resume.new
  end

  def create
    @resume = current_user.resumes.build resume_params
    if @resume.save
      change_visibility(@resume)
      redirect_to action: :index
    else
      render :new
    end
  end

  def update
    @resume = current_user.resumes.find params[:id]
    if @resume.update(resume_params)
      change_visibility(@resume)
      f(:success)
      redirect_to action: :index
    else
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
    attrs = %i[name github_url summary skills_description awards_description english_fluency]
    nested_attrs = {
      educations_attributes: %i[description begin_date end_date current _destroy id],
      works_attributes: %i[company position description begin_date end_date current _destroy id]
    }
    params.require(:resume).permit(*attrs, **nested_attrs)
  end
end
