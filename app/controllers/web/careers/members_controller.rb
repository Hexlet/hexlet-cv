# frozen_string_literal: true

class Web::Careers::MembersController < Web::Careers::ApplicationController
  after_action :verify_authorized, only: %i[show]

  def show
    authorize resource_member
    @career_items = resource_career.items.ordered
    career_step_members = resource_member.career_step_members.ordered
    @career_step_members_by_step_id = career_step_members.index_by(&:career_step_id)
    @career_item = career_item
    @career_step = career_item.career_step
    @career_step_member = resource_member.career_step_members.find_or_create_by!(career_step: @career_step)
  end

  def career_item
    return resource_member.current_item unless params[:step]

    item_index = params[:step].to_i - 1

    if @career_items[item_index] && resource_member.can_show_step_body?(@career_items[item_index])
      @career_items[item_index]
    else
      @career_items.first
    end
  end
end
