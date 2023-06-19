# frozen_string_literal: true

class Web::Admin::CareerMemberUsersController < Web::Admin::ApplicationController
  def create
    @career_member = Career::Member.new(career_member_params)
    if @career_member.save
      f(:success)
    else
      f(:error)
    end

    redirect_to admin_career_users_path
  end

  private

  def career_member_params
    params.require(:career_member).permit(:user_id, :career_id)
  end
end
