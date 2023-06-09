# frozen_string_literal: true

class Web::Admin::Careers::ApplicationController < Web::Admin::ApplicationController
  helper_method :resource_career

  def resource_career
    @resource_career ||= Career.find_by(slug: params[:career_id])
    raise ActiveRecord::RecordNotFound if @resource_career.nil?

    @resource_career
  end
end
