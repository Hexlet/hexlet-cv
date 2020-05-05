# frozen_string_literal: true

class Web::Admin::ResumesController < Web::Admin::ApplicationController
  def index
    @q = Resume.web_admin.ransack(params[:q])
    @resumes = @q.result(distinct: true).page(params[:page])
  end

  def change_admin_state
    @resume = Resume.find params[:id]
    @resume.aasm(:admin_state).fire! params[:event]
    f(:success)
    redirect_to admin_resumes_path
  end
end
