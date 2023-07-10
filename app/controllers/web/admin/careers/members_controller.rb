# frozen_string_literal: true

class Web::Admin::Careers::MembersController < Web::Admin::Careers::ApplicationController
  def new
    @career_member = Career::Member.new
  end

  def create
    @career_member = Career::MemberMutator.create(resource_career, career_member_params)
    if @career_member.persisted?
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
    redirect_to params[:back_to] || admin_career_path(resource_career)
  end

  def activate
    member = Career::Member.find(params[:id])
    member.activate!
    f(:success)
    redirect_to params[:back_to] || admin_career_member_users_path
  end

  private

  def career_member_params
    params.require(:career_member).permit(:user_id)
  end
end
