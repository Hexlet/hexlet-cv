# frozen_string_literal: true

class Web::Account::ResumesController < Web::Account::ApplicationController
  def index
    @resumes = current_user.resumes.with_locale
  end

  def new
    @resume = Resume.new
  end

  def edit
    @resume = current_user.resumes.find params[:id]
    @resume_educations = @resume.educations.web
    @resume_works = @resume.works.web
  end

  def create
    @resume = Web::Account::ResumeForm.new(params[:resume])
    @resume.user = current_user

    change_state(@resume)
    if @resume.save
      OpenAiJob.perform_later(@resume.id)
      f(:success)
      redirect_to account_resumes_path
    else
      f(:error)
      render :new, status: :unprocessable_entity
    end
  end

  def update
    resume = current_user.resumes.find params[:id]
    @resume = resume.becomes(Web::Account::ResumeForm)

    change_state(@resume)
    if @resume.update(params[:resume])
      OpenAiJob.perform_later(@resume.id)
      f(:success)
      redirect_to account_resumes_path
    else
      f(:error)
      render :edit, status: :unprocessable_entity
    end
  end

  def destroy; end

  def preview
    @resume = current_user.resumes.find(params[:id])
    @resume_educations = @resume.educations.web
    @resume_works = @resume.works.web
    if @resume.draft?
      render :preview
    else
      redirect_to account_resumes_path, alert: t('.not_accessible')
    end
  end

  private

  def change_state(resume)
    if params[:publish]
      resume.publish
    else
      resume.hide
    end
  end
end
