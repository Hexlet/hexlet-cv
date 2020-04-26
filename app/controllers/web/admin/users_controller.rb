# frozen_string_literal: true

class Web::Admin::UsersController < Web::Admin::ApplicationController
  def index
    @q = User.web_admin.ransack(params[:q])
    @users = @q.result(distinct: true).page(params[:page])
  end

  def ban
    user = User.find(params[:user_id])
    user.ban!
    f(:success)
    redirect_to admin_users_path
  end

  def unban
    user = User.find(params[:user_id])
    user.unban!
    f(:success)
    redirect_to admin_users_path
  end
end
