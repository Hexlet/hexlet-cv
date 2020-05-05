# frozen_string_literal: true

class Web::Admin::UsersController < Web::Admin::ApplicationController
  def index
    @q = User.web_admin.ransack(params[:q])
    @users = @q.result(distinct: true).page(params[:page])
  end

  def change_admin_state
    @user = User.find params[:id]
    @user.aasm(:admin_state).fire! params[:event]
    f(:success)
    redirect_to admin_users_path
  end
end
