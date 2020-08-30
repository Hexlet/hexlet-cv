# frozen_string_literal: true

class Web::Admin::UsersController < Web::Admin::ApplicationController
  def index
    @q = User.web_admin.ransack(params[:q])
    @users = @q.result(distinct: true).page(params[:page])
  end

  def update
    @user = User.find params[:id]
    user = @user.becomes(Web::Admin::UserForm)
    if user.update(profile_params)
      f(:success)
      redirect_to action: :index
    else
      @user = user.becomes(User)
      render :edit
    end
  end

  def edit
    @user = User.find params[:id]
  end

  private

  def profile_params
    params.require(:user)
  end
end
