# frozen_string_literal: true

class Web::Admin::CareerMemberUsersController < Web::Admin::ApplicationController
  def new
    @career_member = Career::Member.new
    @careers = Career.all
  end

  def create
    @career_member = Career::Member.new(career_member_params)
    if @career_member.save
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
