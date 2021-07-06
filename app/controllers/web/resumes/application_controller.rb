# frozen_string_literal: true

class Web::Resumes::ApplicationController < Web::ApplicationController
  before_action :authenticate_user!
  helper_method :resource_resume

  def resource_resume
    @resource_resume ||= Resume.find(params[:resume_id])
  end
end
