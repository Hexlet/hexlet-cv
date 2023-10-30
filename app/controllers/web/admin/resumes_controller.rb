# frozen_string_literal: true

class Web::Admin::ResumesController < Web::Admin::ApplicationController
  def index
    query = { s: 'created_at desc' }.merge(params.permit![:q] || {})
    respond_to do |format|
      format.html do
        @search = Resume.ransack(query)
        @resumes = @search.result(distinct: true).includes(:user).page(params[:page])
      end

      format.csv do
        search = Resume.ransack(query)
        resumes = search.result(distinct: true).includes(:user)

        headers = %w[id name state user email created_at]
        send_file_headers!(filename: "resumes-#{Time.zone.today}.csv")
        self.response_body = generate_csv(resumes, headers) do |resume|
          [
            resume.id,
            resume.name,
            resume.aasm(:state).human_state,
            resume.user.full_name,
            resume.user.email,
            l(resume.created_at, format: :long)
          ]
        end
      end
    end
  end

  # TODO: сделать возможным просматривать админам архивные и неопубликованные резюме
  def show; end

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
      redirect_to admin_resumes_path
    else
      f(:error)
      render :edit, status: :unprocessable_entity
    end
  end

  def archive
    resume = Resume.find(params[:id])
    resume.archive!
    f(:success)
    redirect_to admin_resumes_path(page: params[:page])
  end

  def restore
    resume = Resume.find(params[:id])
    resume.restore!
    f(:success)
    redirect_to admin_resumes_path(page: params[:page])
  end
end
