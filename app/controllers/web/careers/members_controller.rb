# frozen_string_literal: true

class Web::Careers::MembersController < Web::Careers::ApplicationController
  after_action :verify_authorized, only: %i[show]

  def show
    authorize resource_member
    @career_items = resource_career.items.order(order: :asc)
    career_step_members = resource_member.career_step_members.order(order: :asc)
    @career_step_members_by_order = career_step_members.index_by(&:order)
    item_order = params[:step] || career_step_members.last&.order || 1
    @current_item = @career_items.find_by(order: item_order)
    @current_step = @current_item.career_step
    @current_career_step_member = resource_member.career_step_members.find_or_create_by!(career_step: @current_step, order: @current_item.order)
  end
end
