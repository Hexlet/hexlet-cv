# frozen_string_literal: true

class Web::Careers::MembersController < Web::Careers::ApplicationController
  after_action :verify_authorized, only: %i[show]

  def show
    authorize resource_member
    @career_items = resource_career.items.order(order: :asc)
    @career_step_members = resource_member.career_step_members.ordered

    @career_item = @career_items.find_by(order: item_order)
    @career_step = @career_item.career_step
    @career_step_member = resource_member.career_step_members.find_or_create_by!(career_step: @career_step)
  end

  private

  def item_order
    return @career_items[params[:step].to_i - 1].order if params[:step].present?

    resource_member.current_item&.order || @career_items.first.order
  end
end
