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

  def publish
    @resume = current_user.resumes.find params[:resume_id]
    @resume.publish!
    flash[:success] = t('flash.web.account.resumes.publish.success')
    redirect_to action: :index
  end

  private

  def resume_params
    attrs = %i[name github_url summary skills_description awards_description english_fluency]
    nested_attrs = {
      educations_attributes: %i[institution faculty begin_date end_date],
      works_attributes: %i[company position description begin_date end_date]
    }
    params.require(:resume).permit(*attrs, *nested_attrs)
  end
end
