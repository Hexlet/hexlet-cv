# frozen_string_literal: true

class Web::Admin::Careers::ApplicationController < Web::Admin::ApplicationController
  helper_method :resource_career

  def resource_career
    @resource_career ||= Career.find params[:career_id]
  end
end
