# frozen_string_literal: true

class Web::Careers::MembersController < Web::Careers::ApplicationController
  after_action :verify_authorized, only: %i[show]

  def show
    authorize resource_member
    @career_items = resource_career.items.order(order: :asc)
    @career_step_members = resource_member.career_step_members.ordered

    current_career_item = resource_member.current_item

    item_order = @career_items.find_by(order: params[:step])&.order ||
                 current_career_item&.order ||
                 @career_items.first.order

    @career_item = @career_items.find_by(order: item_order)
    @career_step = @career_item.career_step
    @career_step_member = resource_member.career_step_members.find_or_create_by!(career_step: @career_step)
  end
end
