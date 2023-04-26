# frozen_string_literal: true

class Web::Admin::Careers::MembersController < Web::Admin::Careers::ApplicationController
  def new
    resource_career
    @career_member = Career::Member.new
  end

  def create
    @member = resource_career.members.build(career_member_params)
    if @member.save
      f(:success)
      redirect_to admin_career_path(resource_career)
    else
      resource_career
      @career_member = Career::Member.new
      f(:error)
      render :new
    end
  end

  def archive
    member = Career::Member.find(params[:id])
    member.mark_as_archived!
    f(:success)
    redirect_to admin_career_path(resource_career)
  end

  private

  def career_member_params
    params.require(:career_member).permit(:user_id)
  end
end
