# frozen_string_literal: true

class Web::Admin::ResumesController < Web::Admin::ApplicationController
  def index
    @q = Resume.web_admin.ransack(params[:q])
    @resumes = @q.result(distinct: true).page(params[:page])
  end

  def ban
    resume = Resume.find(params[:resume_id])
    resume.ban!
    f(:success)
    redirect_to admin_resumes_path
  end

  def unban
    resume = Resume.find(params[:resume_id])
    resume.unban!
    f(:success)
    redirect_to admin_resumes_path
  end
end
