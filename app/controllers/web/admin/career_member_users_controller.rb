# frozen_string_literal: true

class Web::Admin::CareerMemberUsersController < Web::Admin::ApplicationController
  def new
    @career_member = Career::Member.new
    @careers = Career.all
  end

  def create
    career = Career.find(career_member_params[:career_id])
    @career_member = Career::MemberMutator.create(career, career_member_params)
    if @career_member.persisted?
      EmailSender.send_new_career_member_email(@career_member)
      f(:success)
      redirect_to admin_career_users_path
    else
      f(:error)
      @careers = Career.all
      render :new
    end
  end

  private

  def career_member_params
    params.require(:career_member).permit(:user_id, :career_id)
  end
end
