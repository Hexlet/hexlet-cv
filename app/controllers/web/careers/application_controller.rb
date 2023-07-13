# frozen_string_literal: true

class Web::Careers::ApplicationController < Web::ApplicationController
  before_action :authenticate_user!
  helper_method :resource_career, :resource_member

  rescue_from Pundit::NotAuthorizedError, with: :user_not_authorized

  def resource_career
    @resource_career ||= Career.find_by!(slug: params[:career_id])
  end

  def resource_member
    @resource_member ||= Career::Member.find params[:id]
  end

  def user_not_authorized
    f(:error)
    redirect_to root_path
  end
end
