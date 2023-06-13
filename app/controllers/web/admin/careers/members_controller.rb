# frozen_string_literal: true

class Web::Admin::Careers::MembersController < Web::Admin::Careers::ApplicationController
  def new
    @career_member = Career::Member.new
  end

  def create
    @career_member = resource_career.members.build(career_member_params)
    if @career_member.save
      EmailSender.send_new_career_member_email(@career_member)
      f(:success)
      redirect_to admin_career_path(resource_career)
    else
      f(:error)
      render :new
    end
  end

  def archive
    member = Career::Member.find(params[:id])
    member.archive!
    f(:success)
    redirect_to admin_career_path(resource_career)
  end

  private

  def career_member_params
    params.require(:career_member).permit(:user_id)
  end
end
