# frozen_string_literal: true

class Web::Resumes::ApplicationController < ApplicationController
  before_action :authenticate_user!

  def resource_resume
    @resource_resume ||= Resume.find(params[:resume_id])
  end
end
