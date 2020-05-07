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

  def update
    @user = User.find params[:id]
    if @user.update(profile_params)
      change_visibility(@user)
      f(:success)
      redirect_to action: :index
    else
      render :edit
    end
  end

  def edit
    @user = User.find params[:id]
  end

  private

  def change_visibility(user)
    user.ban! if params[:ban]
    user.unban! if params[:unban]
  end

  def profile_params
    params.require(:user).permit(:first_name, :last_name, :about)
  end
end
