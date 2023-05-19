# frozen_string_literal: true

class Web::Careers::ApplicationController < Web::ApplicationController
  before_action :authenticate_user!
  helper_method :resource_career, :resource_member

  rescue_from Pundit::NotAuthorizedError, with: :not_allowed

  def resource_career
    @resource_career ||= Career.find params[:career_id]
  end

  def resource_member
    @resource_member ||= Career::Member.find params[:id]
  end

  def not_allowed
    f(:error)
    redirect_to root_path
  end
end
