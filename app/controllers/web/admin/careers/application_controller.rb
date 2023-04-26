# frozen_string_literal: true

class Web::Admin::Careers::ApplicationController < Web::Admin::ApplicationController
  def resource_career
    @resource_career ||= Career.find params[:career_id]
  end
end
