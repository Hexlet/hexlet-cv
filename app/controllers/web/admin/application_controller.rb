# frozen_string_literal: true

class Web::Admin::ApplicationController < Web::ApplicationController
  include CsvConcern
  include ApplicationHelper
  before_action :authenticate_admin!

  helper_method :resource_career

  def resource_career
    @resource_career ||= Career.find_by(slug: params[:id])
    raise ActiveRecord::RecordNotFound if @resource_career.nil?

    @resource_career
  end
end
