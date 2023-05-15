# frozen_string_literal: true

class Web::Careers::MembersController < Web::Careers::ApplicationController
  after_action :verify_authorized, only: %i[show]

  def show
    authorize resource_member
    @career_items = resource_career.items.order(order: :asc)
    career_step_members = resource_member.career_step_members.ordered

    @career_step_members_by_order = {}
    career_step_members.each_with_index do |career_step_member, index|
      @career_step_members_by_order[index + 1] = career_step_member
    end

    career_item = resource_member.current_item
    item_order = params[:step] || career_item&.order || @career_items.first.order
    @current_item = @career_items.find_by(order: item_order)
    @current_step = @current_item.career_step
    @current_career_step_member = resource_member.career_step_members.find_or_create_by!(career_step: @current_step)
  end
end
