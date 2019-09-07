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
      flash[:success] = t('flash.web.account.resumes.create.success')
      redirect_to action: :index
    else
      render :new
    end
  end

  def update
    @resume = current_user.resumes.find params[:id]
    if @resume.update(resume_params)
      flash[:success] = t('flash.web.account.resumes.update.success')
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
    params.require(:resume).permit(*attrs, educations_attributes: %i[institution faculty begin_date end_date],
                                           works_attributes: %i[company position description begin_date end_date])
  end
end
