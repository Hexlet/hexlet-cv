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
      @resume.publish! if params[:publish]
      f(:success)
      redirect_to action: :index
    else
      render :new
    end
  end

  def update
    @resume = current_user.resumes.find params[:id]
    if @resume.update(resume_params)
      @resume.publish! if params[:publish]
      f(:success)
      redirect_to action: :index
    else
      render :edit
    end
  end

  def edit
    @resume = current_user.resumes.find params[:id]
  end

  def destroy; end

  private

  def resume_params
    attrs = %i[name github_url summary skills_description awards_description english_fluency]
    nested_attrs = {
      educations_attributes: %i[institution faculty begin_date_month begin_date_year end_date_month end_date_year _destroy id],
      works_attributes: %i[company position description begin_date_month begin_date_year end_date_month end_date_year _destroy id]
    }
    params.require(:resume).permit(*attrs, **nested_attrs)
  end
end
