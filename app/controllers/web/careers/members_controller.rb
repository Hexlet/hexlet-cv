# frozen_string_literal: true

class Web::Careers::MembersController < Web::Careers::ApplicationController
  after_action :verify_authorized, only: %i[show]
  helper_method :career_item

  def show
    authorize resource_member
    @career_items = resource_career.items.order(order: :asc)
    @career_step_members = resource_member.career_step_members.ordered

    @career_step = career_item.career_step
    @career_step_member = resource_member.career_step_members.find_or_create_by!(career_step: @career_step)
  end

  def career_item
    return resource_member.current_item unless params[:step]

    item_index = params[:step].to_i - 1

    career_item = @career_items[item_index]

    return career_item if career_item

    @career_items.first
  end
end
